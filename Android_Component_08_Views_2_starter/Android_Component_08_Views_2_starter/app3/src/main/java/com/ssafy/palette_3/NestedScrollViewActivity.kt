package com.ssafy.palette_3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.palette_3.databinding.ActivityNestedScrollViewBinding

class NestedScrollViewActivity  : AppCompatActivity() {

    private lateinit var binding: ActivityNestedScrollViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val myAdapter = MyAdapter(arrayOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven", "twelve"))
        binding = ActivityNestedScrollViewBinding.inflate(layoutInflater).apply {
            mainRv2.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context)
                adapter = myAdapter
            }
        }
        setContentView(binding.root)
    }
}

class MyAdapter(private val dataSet: Array<String>) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.item_tv)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.recyclerview_item, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.textView.text = dataSet[position]
    }

    override fun getItemCount() = dataSet.size
}
