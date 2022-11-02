package com.ssafy.note.sample

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class NoteEditActivity : AppCompatActivity() {

    private lateinit var mTitleText: EditText
    private lateinit var mBodyText: EditText
    private var mRowId: Long = -1L
    private lateinit var mDbHelper: NotesDbAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_edit)

        mDbHelper = NotesDbAdapter()
        mDbHelper.open(this)

        mRowId = savedInstanceState?.getLong(NotesDbAdapter.KEY_ROWID) ?: -1L

        // -1 이면 앞 Activity에서 넘어온 경우이므로, intent에서 값을 꺼내서, 있으면 수정. 입력:-1
        if (mRowId == -1L) {
            val extras = intent.extras
            mRowId = extras?.getLong(NotesDbAdapter.KEY_ROWID) ?: -1L
        }

        findViewById<Button>(R.id.confirm).setOnClickListener {
            setResult(RESULT_OK)
            finish()
        }
    }

    private fun initView() {
        mTitleText = findViewById<View>(R.id.title) as EditText
        mBodyText = findViewById<View>(R.id.body) as EditText

        if (mRowId != -1L) {
            val note = mDbHelper.selectNote(mRowId)

            mTitleText.setText(note.TITLE)
            mBodyText.setText(note.BODY)
        }
    }

    //비정상 종료시는 id를 기록한다.
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong(NotesDbAdapter.KEY_ROWID, mRowId)
    }

    //pause에서 저장하도록 구현
    override fun onPause() {
        super.onPause()
        saveState()
    }

    override fun onResume() {
        super.onResume()
        initView()
    }

    //-1은 앞 Activity에서 입력으로 넘어온 경우
    private fun saveState() {
        val title = mTitleText.text.toString()
        val body = mBodyText.text.toString()
        if (mRowId == -1L) {
            mRowId = mDbHelper.insertNote(title, body)
        } else {
            mDbHelper.updateNote(mRowId, title, body)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mDbHelper.close()
    }
}