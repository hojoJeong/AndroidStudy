package com.ssafy.note.sample

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


private const val TAG = "NoteListActivity_싸피"
class NoteListActivity : AppCompatActivity() {
    lateinit private var mDbHelper: NotesDbAdapter

    lateinit var listAdapter:NoteListAdapter

    //DB 연결하고 Adapter 초기화
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes_list)

        mDbHelper = NotesDbAdapter()
        mDbHelper.open(this)

        initAdapter()
    }

    private fun initAdapter(){
        listAdapter = NoteListAdapter()
        listAdapter.listData = getData()
        listAdapter.notifyDataSetChanged()

        findViewById<RecyclerView>(R.id.list_recyle).apply{
            adapter = listAdapter
            layoutManager = LinearLayoutManager(this@NoteListActivity)
        }
    }

    //전체 데이터 조회해서 리턴
    private fun getData():MutableList<NotesDto> {
        val result = mDbHelper.selectAllNotes()
        return result
    }

    //options 메뉴(생성)
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        menu.add(0, INSERT_ID, 0, R.string.menu_insert)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            INSERT_ID -> {
                createNote()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    //생성, 수정 콜백
    val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode == Activity.RESULT_OK){
            Toast.makeText(this, "registerForActivityResult", Toast.LENGTH_SHORT).show()
            refreshAdapter()
        }
    }

    //note 생성
    private fun createNote() {
        val intent = Intent(this, NoteEditActivity::class.java)
        startForResult.launch(intent)
    }

    //note 삭제
    private fun deleteNote(id:Long) {
        mDbHelper.deleteNote(id)
        refreshAdapter()
    }


    private fun refreshAdapter(){
        listAdapter.listData = getData()
        listAdapter.notifyDataSetChanged()
    }

    inner class NoteListAdapter : RecyclerView.Adapter<NoteListAdapter.NoteViewHolder>(){
        lateinit var listData : MutableList<NotesDto>

        inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnCreateContextMenuListener {
            //context Menu 등록.필수.
            init {
                itemView.setOnCreateContextMenuListener(this)
            }

            fun bind(noteDto:NotesDto){
                itemView.findViewById<TextView>(R.id.text1).text = noteDto.TITLE
            }
            //context Menu 생성
            override fun onCreateContextMenu( menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
                var menuItem = menu?.add(0, DELETE_ID, 0, "Delete Memo");
                //context menu event 처리
                menuItem?.setOnMenuItemClickListener( object: MenuItem.OnMenuItemClickListener{
                    override fun onMenuItemClick(item: MenuItem?): Boolean {
                        this@NoteListActivity.deleteNote(listData[layoutPosition]._ID)
                        return true
                    }
                })
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteListAdapter.NoteViewHolder {
            var view = LayoutInflater.from(parent.context).inflate(R.layout.notes_row, parent, false)
            return NoteViewHolder(view).apply{
                //목록 선택 event 처리  --> 수정으로 보냄.
                itemView.setOnClickListener{
                    val intent = Intent(parent.context, NoteEditActivity::class.java)
                    intent.putExtra(NotesDbAdapter.KEY_ROWID, listData[layoutPosition]._ID)
                    this@NoteListActivity.startForResult.launch(intent)
                }
            }
        }

        override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
            var noteDto = listData[position]
            holder.bind(noteDto)
        }

        override fun getItemCount(): Int {
            return listData.size
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mDbHelper.close()
    }

    companion object {
        private const val INSERT_ID = Menu.FIRST
        private const val DELETE_ID = Menu.FIRST + 1
    }
}


