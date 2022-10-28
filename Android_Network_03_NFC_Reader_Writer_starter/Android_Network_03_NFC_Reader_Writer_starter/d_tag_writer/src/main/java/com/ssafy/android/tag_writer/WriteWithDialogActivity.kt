package com.ssafy.android.tag_writer

import android.app.PendingIntent
import android.content.DialogInterface
import android.content.Intent
import android.content.IntentFilter
import android.content.IntentFilter.MalformedMimeTypeException
import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.Ndef
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.android.tag_writer.databinding.ActivityWriteWithDialogBinding

private const val TAG = "WriteWithDialog_싸피"
class WriteWithDialogActivity : AppCompatActivity() {

    private var mWriteMode = false
    private lateinit var mNfcAdapter: NfcAdapter
    private lateinit var dialog: AlertDialog
    private lateinit var pIntent: PendingIntent
    private lateinit var mNdefFilters: Array<IntentFilter>
    private lateinit var binding: ActivityWriteWithDialogBinding
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWriteWithDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this)
        binding.writeTag.setOnClickListener( object : View.OnClickListener {
                override fun onClick(view: View) {
                    mWriteMode = true
                    dialog = AlertDialog.Builder(this@WriteWithDialogActivity)
                        .setTitle("Touch tag to write")
                        .setMessage("기록하려는 NFC 카드를 접촉해 주세요.")
                        .setOnCancelListener(
                            DialogInterface.OnCancelListener { mWriteMode = false }).create()
                    dialog.show()
                }
            })

        // Handle all of our received NFC intents in this activity.
        pIntent = PendingIntent.getActivity(this, 0, Intent(this, javaClass)
            .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), PendingIntent.FLAG_MUTABLE)

        // Intent filters for reading a note from a tag or exchanging over p2p.
        val ndefFilter1 = IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED)
        val ndefFilter2 = IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED)
        try {
            ndefFilter1.addDataType("text/plain")
            ndefFilter2.addDataScheme("https")
        } catch (e: MalformedMimeTypeException) {
            e.printStackTrace()
        }

        mNdefFilters = arrayOf(ndefFilter1, ndefFilter2)
    }

    override fun onResume() {
        super.onResume()
        mNfcAdapter.enableForegroundDispatch(this, pIntent, mNdefFilters, null)
    }

    override fun onPause() {
        super.onPause()
        mNfcAdapter.disableForegroundDispatch(this)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        // NDEF exchange mode
        if (mWriteMode && (NfcAdapter.ACTION_NDEF_DISCOVERED == intent.action)) {
            val detectedTag = intent.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)
            if (writeTag(makeNdefMessage(), detectedTag) && dialog.isShowing) {
                mWriteMode = false
                dialog.dismiss()
            }
        } else if (!mWriteMode && (NfcAdapter.ACTION_NDEF_DISCOVERED == intent.action)) {
            val msgs = getNdefMessages(intent)
            promptForContent(msgs!![0])
        }
    }

    private fun promptForContent(msg: NdefMessage?) {
        AlertDialog.Builder(this)
            .setTitle("새로운 Tag가 인식되었습니다.\n 읽으시겠습니까?")
            .setPositiveButton("Yes",
                object : DialogInterface.OnClickListener {
                    override fun onClick(arg0: DialogInterface, arg1: Int) {
                        val body = String(msg!!.records[0].payload)
                        binding.note.setText(body)
                    }
                })
            .setNegativeButton("No", object : DialogInterface.OnClickListener {
                override fun onClick(arg0: DialogInterface, arg1: Int) {}
            }).show()
    }

    private fun makeNdefMessage(): NdefMessage {
        val textRecord = NdefRecord.createTextRecord("en", binding.note.text.toString())
        return NdefMessage(arrayOf(textRecord))
    }

    private fun getNdefMessages(intent: Intent): Array<NdefMessage?>? {
        // Parse the intent
        var msgs: Array<NdefMessage?>? = null
        val action = intent.action
        if ((NfcAdapter.ACTION_NDEF_DISCOVERED == action)) {
            val rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)
            if (rawMsgs != null) {
                msgs = arrayOfNulls(rawMsgs.size)
                for (i in rawMsgs.indices) {
                    msgs[i] = rawMsgs[i] as NdefMessage
                }
            } else {
                // Unknown tag type
                val empty = byteArrayOf()
                val record = NdefRecord(NdefRecord.TNF_UNKNOWN, empty, empty, empty)
                val msg = NdefMessage(arrayOf(record))
                msgs = arrayOf(msg)
            }
        } else {
            Log.d(TAG, "Unknown intent.")
            finish()
        }
        return msgs
    }

    fun writeTag(message: NdefMessage, tag: Tag?): Boolean {
        val size = message.toByteArray().size
        try {
            val ndef = Ndef.get(tag)
            if (ndef != null) {
                ndef.connect()
                if (!ndef.isWritable) {
                    toast("Tag is read-only.")
                    return false
                }
                if (ndef.maxSize < size) {
                    toast( ("Tag capacity is " + ndef.maxSize + " bytes, message is " + size + " bytes.") )
                    return false
                }
                ndef.writeNdefMessage(message)
                toast("기록하였습니다.")
                // Ndef 객체의 makeReadOnly()를 사용하면 overwrite 안되게 설정이 가능하다.
                //복구 불가능할 수 있으니, 주의
                return true
            } else {
                toast("Tag doesn't support NDEF.")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            toast("Failed to write tag")
        }
        return false
    }

    private fun toast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

}