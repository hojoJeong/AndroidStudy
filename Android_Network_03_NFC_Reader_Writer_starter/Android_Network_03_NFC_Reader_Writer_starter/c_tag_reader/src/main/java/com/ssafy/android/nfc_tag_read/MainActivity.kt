package com.ssafy.android.nfc_tag_read

import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.content.IntentFilter.MalformedMimeTypeException
import android.nfc.NdefMessage
import android.nfc.NfcAdapter
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.android.nfc_tag_read.databinding.ActivityMainBinding

private const val TAG = "MainActivity_싸피"
class MainActivity : AppCompatActivity() {
    private lateinit var nAdapter: NfcAdapter
    private lateinit var pIntent: PendingIntent
    private lateinit var filters: Array<IntentFilter>
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        nAdapter = NfcAdapter.getDefaultAdapter(this)

        val i = Intent(this, MainActivity::class.java)
        i.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        pIntent = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_MUTABLE)

        val filter = IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED)
        filters = arrayOf(filter)
//        val filter1 = IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED)
//        filter1.addDataType("text/plain")
//        val filter2 = IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED)
//        filter2.addDataType("https")
//        filters = arrayOf(filter1, filter2)

    }

    override fun onResume() {
        super.onResume()

        nAdapter.enableForegroundDispatch(this, pIntent, filters, null)
    }

    override fun onPause() {
        super.onPause()
        nAdapter.disableForegroundDispatch(this)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        // 이후 다른 method에서 getIntent() 호출시 newIntent에서 받은 Intent를 사용하기 위함.
        // set하지 않으면 기존의 intent인 action Main이 나옴.
        setIntent(intent)
        getNFCData(getIntent())
//        setIntent(Intent())
    }

    private fun getNFCData(intent: Intent) {
        Toast.makeText(this, "수신 액션 : " + getIntent().action, Toast.LENGTH_SHORT).show()
        val action = intent.action
        Log.d(TAG, "getNFCData: " + action)
        if (action == NfcAdapter.ACTION_NDEF_DISCOVERED || action == NfcAdapter.ACTION_TECH_DISCOVERED || action == NfcAdapter.ACTION_TAG_DISCOVERED) {
            val message = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)!![0] as NdefMessage

            Log.d(TAG, "getNFCData message.records.size : ${message.records.size}")

            for(record in message.records){
                Log.d(TAG, "getNFCData record.id : ${String(record.id)}")
                Log.d(TAG, "getNFCData record.type : ${String(record.type)}")
                Log.d(TAG, "getNFCData record.tnf : ${record.tnf}")
                Log.d(TAG, "getNFCData: payload : ${String(record.payload)}")

                val type = String(record.type)
                val payload = record.payload
                when(type){
                    "T" -> {

                    }
                    "U" -> {
                        val uri = record.toUri()
                        startActivity( Intent().apply {
                            setAction(Intent.ACTION_VIEW)
                            setData(uri)
                        })
                    }
                    "Sp" -> {
                        val uri = record.toUri()
                        startActivity( Intent().apply {
                            setAction(Intent.ACTION_SENDTO)
                            setData(uri)
                        })
                    }
                }
            }
        }
    }
}