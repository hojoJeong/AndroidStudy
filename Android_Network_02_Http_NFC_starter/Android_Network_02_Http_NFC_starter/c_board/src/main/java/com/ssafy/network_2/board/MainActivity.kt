package com.ssafy.network_2.board

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.network_2.board.databinding.ActivityMainBinding
import com.ssafy.network_2.board.model.Board
import com.ssafy.network_2.board.service.BoardService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "MainActivity_싸피"
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var adapter:MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var list = getAllBoard()
        adapter = MyAdapter(list)
        adapter.setOnBoardClickListener(object : MyAdapter.OnBoardClickListener{
            override fun onBoardItemClick(view: View, board: Board, position: Int) {
                var intent = Intent(this@MainActivity, DetailActivity::class.java).apply {
                    putExtra("no", board.no.toString())
                }
                startActivity(intent)
            }
        })

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        binding.buttonWrite.setOnClickListener{
            startActivity(Intent(this, WriteActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        getAllBoard()
    }

    private fun getAllBoard():MutableList<Board>{
        var result = mutableListOf<Board>()
        val service = ApplicationClass.retrofit.create(BoardService::class.java)
        service.selectAll().enqueue(object : Callback<MutableList<Board>> {
            override fun onResponse(call: Call<MutableList<Board>>, response: Response<MutableList<Board>>) {
                if(response.code() == 200) {
                    result = response.body() ?: mutableListOf<Board>()
                    adapter.list = result
                    adapter.notifyDataSetChanged()
                    Log.d(TAG, "onResponse: ${result}")
                } else {
                    Log.d(TAG, "getAllBoard - onResponse : Error code ${response.code()}")
                }
            }

            override fun onFailure(call: Call<MutableList<Board>>, t: Throwable) {
                Log.d(TAG, t.message ?: "통신오류")
            }
        })
        return result
    }

    class MyAdapter(var list : MutableList<Board>): RecyclerView.Adapter<MyAdapter.CustomViewHolder>(){

        class CustomViewHolder (itemView: View):RecyclerView.ViewHolder(itemView) {
            var no:TextView = itemView.findViewById(R.id.no)
            var title:TextView = itemView.findViewById(R.id.title)
            var regDate:TextView =itemView.findViewById(R.id.regDate)

            fun bindInfo(data: Board){
                no.text = data.no.toString()
                title.text = data.title
                regDate.text = data.regtime

            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)

            return CustomViewHolder(view).apply{
                view.setOnClickListener{
                    Log.d(TAG, "onCreateViewHolder: adapterPosition:${adapterPosition}, layoutPosition: ${layoutPosition}")
                    boardClickListener.onBoardItemClick(view, list[layoutPosition], layoutPosition )
                }
            }
            //여기서 event handler를 선언할 수 있으나,
            //여기는 holder생성만 담당하는것이 맞고, 이벤트 처리는 바깥 클래스로 빼서 하는것이 더 좋음.
//            return CustomViewHolder(view).apply{
//                view.setOnClickListener{
//                    var selected = list[layoutPosition]
//
//                    var intent = Intent(parent.context, DetailActivity::class.java).apply {
//                        putExtra("no", selected.no.toString())
//                    }
//                    parent.context.startActivity(intent)
//                    Toast.makeText(parent.context, "clicked:${selected.no}", Toast.LENGTH_SHORT).show();
//                }
//            }
        }

        override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
            Log.d(TAG, "onBindViewHolder: ${list.get(position)}")
            holder.bindInfo(list.get(position))
        }

        override fun getItemCount(): Int {
            return list.size
        }

        /*이벤트 처리를 위한 Listener*/
        //클릭 인터페이스 정의. Activity에서 이 interface 구현체를 만들어서 호출한다.
        interface OnBoardClickListener {
            fun onBoardItemClick(view: View, board: Board, position: Int)
        }

        //클릭리스너 선언
        private lateinit var boardClickListener: OnBoardClickListener

        //클릭리스너 등록 매소드
        fun setOnBoardClickListener(boardClickListener: OnBoardClickListener) {
            this.boardClickListener = boardClickListener
        }


    }
}