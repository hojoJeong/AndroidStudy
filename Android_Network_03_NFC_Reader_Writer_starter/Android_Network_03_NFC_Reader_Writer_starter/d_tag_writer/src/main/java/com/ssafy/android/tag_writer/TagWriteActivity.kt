package com.ssafy.android.tag_writer

import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.Ndef
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.android.tag_writer.databinding.ActivityTagWriteBinding

private const val TAG = "TagWriteActivity_싸피"
class TagWriteActivity : AppCompatActivity() {
    private lateinit var nfcAdapter: NfcAdapter
    private lateinit var pIntent: PendingIntent
    private lateinit var filters: Array<IntentFilter>
    private lateinit var tagType: String
    private lateinit var tagData: String
    
    private lateinit var binding: ActivityTagWriteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTagWriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        nfcAdapter = NfcAdapter.getDefaultAdapter(this)
        if (nfcAdapter == null) {
            finish()
        }

        //넘어온 데이터를 변수에 저장한다.
        tagType = intent.getStringExtra("type").toString()
        tagData = intent.getStringExtra("data").toString()
        Toast.makeText(this, "type : $tagType, data : $tagData", Toast.LENGTH_SHORT).show()

        //태그 정보가 포함된 인텐트를 처리할 액티비티 지정
        val intent = Intent(this, TagWriteActivity::class.java)

        //SingleTop설정
        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        pIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_MUTABLE)

        val tagFilter = IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED)
        filters = arrayOf(tagFilter)
    }

    public override fun onResume() {
        super.onResume()
        nfcAdapter.enableForegroundDispatch(this, pIntent, filters, null)
    }

    public override fun onPause() {
        super.onPause()
        nfcAdapter.disableForegroundDispatch(this)
    }

    public override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        //태그에 데이터를 write 작업을 수행해야 한다..
        val action = intent.action
        if (action == NfcAdapter.ACTION_NDEF_DISCOVERED || action == NfcAdapter.ACTION_TECH_DISCOVERED || action == NfcAdapter.ACTION_TAG_DISCOVERED) {
            //Log.d(TAG, "ACTION_NDEF_DISCOVERED...")
            //1. 태그 detect.... Tag 객체
            val detectTag = intent.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)
            //writeTag 함수 호출
            writeTag(makeNdefMessage(tagType, tagData), detectTag)
        }
    }

    //T, "SSAFY"  / U, "https://m.naver.com"
    private fun makeNdefMessage(type: String?, data: String?): NdefMessage {
        var ndefR: NdefRecord? = null
        var ndefR1: NdefRecord? = null
        if (type == "T") {//TextRecord
            ndefR = NdefRecord.createTextRecord("en", data)
            ndefR1 = NdefRecord.createApplicationRecord("com.ssafy.android.tag_recognition")
        } else if (type == "U") {//URI
            ndefR = NdefRecord.createUri(data)
        } else {
            //다른 형태....
        }
        return NdefMessage(arrayOf(ndefR, ndefR1))
    }

    //NFC tag 에 데이터를 write 코드...
    private fun writeTag(msg: NdefMessage, tag: Tag?) {

        //Ndef 객체를 얻는다 : Ndef.get(tag)
        val ndef = Ndef.get(tag)
        val msgSize = msg.toByteArray().size
        try {
            if (ndef != null) {

                //ndef 객체를 이용해서 connect
                ndef.connect()
                //tag가 write모드를 지원하는지 여부 체크
                if (!ndef.isWritable) {
                    Toast.makeText(this, "Write를 지원하지 않습니다..", Toast.LENGTH_SHORT).show()
                    return
                }
                if (ndef.maxSize < msgSize) {
                    Toast.makeText(this, "Write할 데이터가 태그 크기보다 큽니다..", Toast.LENGTH_SHORT).show()
                    return
                }

                //ndef객체의 writeNdefMessage(msg) 태그에 write 한다...
                ndef.writeNdefMessage(msg)
                Toast.makeText(this, "태그에 데이터를 write 하였습니다..", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Failed to write tag", Toast.LENGTH_SHORT).show()
        }
    }
}