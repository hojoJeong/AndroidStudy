package com.ssafy.network_2.board

import android.os.Bundle
import android.provider.SyncStateContract.Helpers.update
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.network_2.board.databinding.ActivityWriteBinding
import com.ssafy.network_2.board.model.Board
import com.ssafy.network_2.board.service.BoardService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "WriteActivity_싸피"
class WriteActivity : AppCompatActivity() {

    lateinit var binding: ActivityWriteBinding

    private var no = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        no = intent.getStringExtra("no")?.toInt() ?: -1 // 수정일경우 넘어옴.

        if( no >= 0 ) {
            getData()
        }

        initEvent()
    }

    private fun initEvent(){
        binding.buttonSave.setOnClickListener{
            if(no >= 0){
                update()
            }else{
                save()
            }
        }

        binding.buttonInit.setOnClickListener{
            binding.title.setText("")
            binding.content.setText("")
        }

        binding.buttonClose.setOnClickListener{
            finish()
        }
    }

    private fun getData(){
        val service = ApplicationClass.retrofit.create(BoardService::class.java)
        service.selectBoard(no.toString()).enqueue( object : Callback<Board>{
            override fun onResponse(call: Call<Board>, response: Response<Board>) {
                if(response.isSuccessful){
                    initView(response.body() as Board)
                }else{
                    Log.d(TAG, "selectBoard - onResponse : Error code ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Board>, t: Throwable) {
                Log.d(TAG, t.message ?: "통신오류")
            }

        })
    }

    private fun initView(board: Board){
        //수정으로 제목변경
        binding.textViewTitle.text = "게시글 수정"

        binding.no.text = board.no.toString()
        binding.regTime.text = board.regtime.toString()

        //editText는 setText로 assign
        binding.writer.setText(board.writer)
        binding.title.setText(board.title.toString())
        binding.content.setText(board.content.toString())
    }

    fun save(){
        var board = Board().apply{
            writer = binding.writer.text.toString()
            title = binding.title.text.toString()
            content = binding.content.text.toString()
        }

        val service = ApplicationClass.retrofit.create(BoardService::class.java)
        service.insertBoard(board).enqueue(object:Callback<Unit>{
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if(response.isSuccessful){
                    Toast.makeText(this@WriteActivity, "저장하였습니다.", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                Log.d(TAG, t.message ?: "통신오류")
            }
        })
    }

    fun update(){
        var board = Board().apply{
            writer = binding.writer.text.toString()
            title = binding.title.text.toString()
            content = binding.content.text.toString()
        }

        val service = ApplicationClass.retrofit.create(BoardService::class.java)
        service.updateBoard(no.toString(), board).enqueue(object:Callback<Unit>{
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if(response.isSuccessful){
                    Toast.makeText(this@WriteActivity, "수정하였습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                Log.d(TAG, t.message ?: "통신오류")
            }
        })
    }


}