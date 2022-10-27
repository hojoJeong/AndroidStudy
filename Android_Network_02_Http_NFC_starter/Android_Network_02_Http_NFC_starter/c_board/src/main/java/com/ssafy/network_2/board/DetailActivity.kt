package com.ssafy.network_2.board

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.network_2.board.databinding.ActivityDetailBinding
import com.ssafy.network_2.board.model.Board
import com.ssafy.network_2.board.service.BoardService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "DetailActivity_싸피"
class DetailActivity : AppCompatActivity() {
    lateinit var binding:ActivityDetailBinding
    var no = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        no = intent.getStringExtra("no")?.toInt() ?: -1
        Log.d(TAG, "onCreate: ${no}")

        if(savedInstanceState != null){
            no = savedInstanceState.getInt("no")
        }
    }

    override fun onResume() {
        super.onResume()

        getData()
        initEvent()
    }

    private fun getData(){
        val service = ApplicationClass.retrofit.create(BoardService::class.java)
        service.selectBoard(no.toString()).enqueue( object : Callback<Board>{
            override fun onResponse(call: Call<Board>, response: Response<Board>) {
                if(response.isSuccessful){
                    fillData(response.body() as Board)
                }else{
                    Log.d(TAG, "selectBoard - onResponse : Error code ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Board>, t: Throwable) {
                Log.d(TAG, t.message ?: "통신오류")
            }

        })
    }

    private fun fillData(board:Board){
        Log.d(TAG, "fillData: $board")
        binding.no.text = board.no.toString()
        binding.title.text = board.title
        binding.writer.text = board.writer
        binding.content.text = board.content

    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putString("no", no.toString())
    }

    private fun initEvent() {
        binding.buttonClose.setOnClickListener{
            finish()
        }
        binding.buttonDelete.setOnClickListener{
            //다이얼로그
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Confirm")
            builder.setMessage("삭제할까요?")
            builder.setIcon(R.drawable.ic_baseline_warning_24)

            val listener = DialogInterface.OnClickListener{ _, i: Int ->
                when(i){
                    DialogInterface.BUTTON_POSITIVE -> {
                        //delete 호출.
                        deleteData()
                    }
                    DialogInterface.BUTTON_NEGATIVE -> {
                        Toast.makeText(this@DetailActivity, "cancelled", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            builder.setPositiveButton("삭제", listener)
            builder.setNegativeButton("취소", listener)
            builder.show()
        }

        binding.buttonEdit.setOnClickListener{
            startActivity(Intent(this, WriteActivity::class.java).apply{
                putExtra("no", no.toString())
            })
        }

    }

    private fun deleteData(){
        val service = ApplicationClass.retrofit.create(BoardService::class.java)
        service.deleteBoard(no.toString()).enqueue(object: Callback<Unit>{
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if(response.isSuccessful){
                    finish() //삭제 성공하면 닫기
                }else{
                    Log.d(TAG, "deleteBoard - onResponse : Error code ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                Log.d(TAG, t.message ?: "통신오류")
            }
        })
    }


}