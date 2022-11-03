package com.ssafy.jetpackall

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.jetpackall.database.NotesDto
import com.ssafy.jetpackall.databinding.NotesRowBinding
import com.ssafy.jetpackall.repository.NoteRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "NoteListActivity_싸피"
class NoteListActivity : AppCompatActivity() {

    private val noteListViewModel:NoteListViewModel by viewModels()
    lateinit var noteRepository: NoteRepository

    private lateinit var listAdapter: NoteListAdapter
    //DB 연결하고 Adapter 초기화
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes_list)
        noteRepository = NoteRepository.get()
        initAdapter()
    }

    private fun initAdapter(){
        listAdapter = NoteListAdapter()
        noteListViewModel.noteList.observe(this) {
            listAdapter.listData = it
            listAdapter.notifyDataSetChanged()
        }

        findViewById<RecyclerView>(R.id.list_recyle).apply{
            adapter = listAdapter
            layoutManager = LinearLayoutManager(this@NoteListActivity)
        }
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

    //note 생성
    private fun createNote() {
        val intent = Intent(this, NoteEditActivity::class.java)
        startActivity(intent)
    }

    //note 삭제
    private fun deleteNote(id:Long) {
        //mDbHelper.deleteNote(id)
        //observe data를 삭제할때는, 삭제도 변경이므로 다시 호출
        noteListViewModel.note(id).observe(this) {
            if (it == null) { //삭제성공시 다시 호출됨.
            } else {
                CoroutineScope(Dispatchers.IO).launch {
                    noteRepository.deleteNote(it)
                }
            }
        }
    }

    inner class NoteListAdapter : RecyclerView.Adapter<NoteListAdapter.NoteViewHolder>(){
        var listData : List<NotesDto> = emptyList()

        inner class NoteViewHolder(private val binding: NotesRowBinding) : RecyclerView.ViewHolder(binding.root), View.OnCreateContextMenuListener {
            //context Menu 등록.필수.
            init {
                binding.viewData = NoteData()
                itemView.setOnCreateContextMenuListener(this)
            }

            fun bind(noteDto: NotesDto){
                binding.apply{
                    viewData?.note = noteDto
                    executePendingBindings()
                }
            }
            //context Menu 생성
            override fun onCreateContextMenu( menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
                val menuItem = menu?.add(0, DELETE_ID, 0, "Delete Memo");
                //context menu event 처리
                menuItem?.setOnMenuItemClickListener( object: MenuItem.OnMenuItemClickListener{
                    override fun onMenuItemClick(item: MenuItem?): Boolean {
                        this@NoteListActivity.deleteNote(listData[layoutPosition].ID)
                        return true
                    }
                })
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
            val binding = DataBindingUtil.inflate<NotesRowBinding>(LayoutInflater.from(parent.context),R.layout.notes_row,parent,false)

            return NoteViewHolder(binding).apply{
                //목록 선택 event 처리  --> 수정으로 보냄.
                itemView.setOnClickListener{
                    val intent = Intent(parent.context, NoteEditActivity::class.java)
                    intent.putExtra(KEY_ROWID, listData[layoutPosition].ID)
                    startActivity(intent)
                }
            }
        }

        override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
            val noteDto = listData[position]
            holder.bind(noteDto)
        }

        override fun getItemCount(): Int {
            return listData.size
        }
    }


    companion object {
        private const val INSERT_ID = Menu.FIRST
        private const val DELETE_ID = Menu.FIRST + 1
    }
}


