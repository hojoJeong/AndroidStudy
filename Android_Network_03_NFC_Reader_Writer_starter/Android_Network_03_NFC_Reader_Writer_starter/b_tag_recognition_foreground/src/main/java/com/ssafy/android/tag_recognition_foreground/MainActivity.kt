package com.ssafy.android.tag_recognition_foreground

import androidx.appcompat.app.AppCompatActivity
import android.nfc.NfcAdapter
import android.app.PendingIntent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.Toast
import android.content.Intent
import android.content.IntentFilter.MalformedMimeTypeException
import android.nfc.NdefMessage
import android.util.Log
import com.ssafy.android.tag_recognition_foreground.databinding.ActivityMainBinding

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

        val action = intent.action
        binding.textview.text = action
        Toast.makeText(this, "onCreate", Toast.LENGTH_SHORT).show()

        nAdapter = NfcAdapter.getDefaultAdapter(this)
        val i = Intent(this, MainActivity::class.java ) //자기자신으로 다시 호출. MainActivity::class.java == javaClass
        i.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        pIntent = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_MUTABLE)

        //수신할 태그 데이터 관련 필터 생성
        //Text Record 수신하기 위한 필터
        val ndef_filter = IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED)
        ndef_filter.addDataType("text/plain")

        val ndef_filter1 = IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED)
        ndef_filter1.addDataScheme("https")


        filters = arrayOf(ndef_filter, ndef_filter1)
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
        Toast.makeText(this, "onNewIntent", Toast.LENGTH_SHORT).show()
        val action = intent.action
        Log.d(TAG, "New Intent action : $action")
        parseData(intent)
    }

    private fun parseData(intent: Intent) {
        val data = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)
        val message = data!![0] as NdefMessage
        val record = message.records[0]
        Log.d(TAG, "parseData: ${String(record.payload)}")
    }

}