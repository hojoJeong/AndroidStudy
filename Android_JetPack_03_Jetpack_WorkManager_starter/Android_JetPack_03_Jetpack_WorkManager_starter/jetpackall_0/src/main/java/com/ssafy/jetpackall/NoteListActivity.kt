package com.ssafy.jetpackall

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.jetpackall.database.NotesDto
import com.ssafy.jetpackall.databinding.NotesRowBinding
import com.ssafy.jetpackall.repository.NoteRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//기존 room_sol을 package만 변경한 형태임.
private const val TAG = "NoteListActivity_싸피"

class NoteListActivity : AppCompatActivity() {

    lateinit var noteRepository: NoteRepository
    private lateinit var listAdapter: NoteListAdapter
    private val noteListViewModel: NoteListViewModel by viewModels()

    //DB 연결하고 Adapter 초기화
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes_list)

        noteRepository = NoteRepository.get()

        initAdapter()
    }

    private fun initAdapter() {
        listAdapter = NoteListAdapter()
        noteListViewModel.noteList.observe(this) {
            listAdapter.listData = it
            listAdapter.notifyDataSetChanged()
        }

        findViewById<RecyclerView>(R.id.list_recyle).apply {
            adapter = listAdapter
            layoutManager = LinearLayoutManager(this@NoteListActivity)
        }
    }

    //전체 데이터 조회해서 리턴
//    private fun getData(): LiveData<MutableList<NotesDto>> {
//        return noteRepository.getNotes()
//    }

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
//    val startForResult =
//        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
//            if (it.resultCode == Activity.RESULT_OK) {
//                refreshAdapter()
//            }
//        }

    //note 생성
    private fun createNote() {
        val intent = Intent(this, NoteEditActivity::class.java)
        startActivity(intent)
//        startForResult.launch(intent)
    }

    //note 삭제
    private fun deleteNote(id: Long) {
        //mDbHelper.deleteNote(id)

        //observe 안쓰고 이렇게 해도 됨
//        val del = noteRepository.getNote(id).value!!
//        CoroutineScope(Dispatchers.IO).launch{
//            noteRepository.deleteNote(del)
//            refreshAdapter()
//        }

        noteRepository.getNote(id).observe(this) {
            if (it == null) {   //삭제되면 변화가 있었으므로 다시 observe 에서 호출되는데 이걸 예방하기 위함

            } else {
                CoroutineScope(Dispatchers.IO).launch {
                    noteRepository.deleteNote(it)
                }
            }
        }
    }

    //observe를 하면 데이터가 변경되었을 때 UI를 변경하는 기능이 있으므로 notifydatasetchange로 퉁 칠 수 있음
//    private fun refreshAdapter() {
//        getData().observe(this) {
//            listAdapter.listData = it
//            listAdapter.notifyDataSetChanged()
//        }
//    }

    inner class NoteListAdapter : RecyclerView.Adapter<NoteListAdapter.NoteViewHolder>() {
        var listData: List<NotesDto> = emptyList()

        inner class NoteViewHolder(var binding: NotesRowBinding) : RecyclerView.ViewHolder(binding.root),

            View.OnCreateContextMenuListener {
            //context Menu 등록.필수.
            init {
                binding.data = NoteData()
                itemView.setOnCreateContextMenuListener(this)
            }

            fun bind(noteDto: NotesDto) {
                binding.data?.note = noteDto
                binding.executePendingBindings()
//                itemView.findViewById<TextView>(R.id.text1).text = noteDto.TITLE
            }
            //context Menu 생성
            override fun onCreateContextMenu(
                menu: ContextMenu?,
                v: View?,
                menuInfo: ContextMenu.ContextMenuInfo?
            ) {
                val menuItem = menu?.add(0, DELETE_ID, 0, "Delete Memo");
                //context menu event 처리
                menuItem?.setOnMenuItemClickListener(object : MenuItem.OnMenuItemClickListener {
                    override fun onMenuItemClick(item: MenuItem?): Boolean {
                        this@NoteListActivity.deleteNote(listData[layoutPosition].ID)
                        return true
                    }
                })
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
            val binding = NotesRowBinding.inflate(LayoutInflater.from(parent.context), parent, false )
//            val binding = DataBindingUtil.inflate<NotesRowBinding>(LayoutInflater.from(parent.context),R.layout.notes_row,parent,false)
            val view = binding.root
//            val view = LayoutInflater.from(parent.context).inflate(R.layout.notes_row, parent, false)
            return NoteViewHolder(binding).apply {
                //목록 선택 event 처리  --> 수정으로 보냄.
                itemView.setOnClickListener {
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


