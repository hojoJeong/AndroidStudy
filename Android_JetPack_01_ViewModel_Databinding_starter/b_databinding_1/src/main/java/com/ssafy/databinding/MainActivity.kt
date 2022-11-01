package com.ssafy.databinding

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import com.ssafy.databinding.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this,
            R.layout.activity_main
        )

        val list = ArrayList<YoutubeDto>()
        list.add(YoutubeDto(R.drawable.image01, getString(R.string.title01)))
        list.add(YoutubeDto(R.drawable.image02, getString(R.string.title02)))
        list.add(YoutubeDto(R.drawable.image03, getString(R.string.title03)))
        list.add(YoutubeDto(R.drawable.image04, getString(R.string.title04)))
        list.add(YoutubeDto(R.drawable.image05, getString(R.string.title05)))
        list.add(YoutubeDto(R.drawable.image06, getString(R.string.title06)))
        list.add(YoutubeDto(R.drawable.image07, getString(R.string.title07)))
        list.add(YoutubeDto(R.drawable.image08, getString(R.string.title08)))
        list.add(YoutubeDto(R.drawable.image09, getString(R.string.title09)))
        list.add(YoutubeDto(R.drawable.image10, getString(R.string.title10)))

        val adapter = YoutubeRecyclerAdapter(this, list)

        binding.recyclerView.adapter = adapter
        binding.recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }

    fun onClickListener(view: View) {
        Toast.makeText(view.context, "Clicked: ${(view.tag as YoutubeDto).title}", Toast.LENGTH_SHORT).show()
    }

}