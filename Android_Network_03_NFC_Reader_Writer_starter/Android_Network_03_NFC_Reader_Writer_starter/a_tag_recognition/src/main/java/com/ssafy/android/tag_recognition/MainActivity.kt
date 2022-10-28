package com.ssafy.android.tag_recognition

import android.nfc.NdefMessage
import android.nfc.NfcAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.ssafy.android.tag_recognition.databinding.ActivityMainBinding

private const val TAG = "MainActivity_싸피"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val action = intent.action
        binding.textview.text = action
        Toast.makeText(this, "onCreate$action", Toast.LENGTH_SHORT).show()
        Log.d(TAG, "onCreate: $action")
        
        //main action과는 구분이 필요
        //NDF Discovered일 때만 읽기 가능
        if(action == NfcAdapter.ACTION_NDEF_DISCOVERED){
            val message = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)!![0] as NdefMessage // n개의 message 중 0번(우리는 지금 데이터가 하나니까)
            Log.d(TAG, "onCreate: $message")
            
            val record = message.records[0] //n개의 record중 0번 -> 우리는 지금 데이터가 하나니까
            Log.d(TAG, "onCreate: recode.type ${String(record.type)}")
            val byteArray = record.payload

            val readString = String(byteArray, 3, byteArray.size-3)
            Log.d(TAG, "onCreate: $readString")
            binding.textview.text = readString
        }
    }

    @Override
    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this, "onDestroy", Toast.LENGTH_SHORT).show()
    }
}