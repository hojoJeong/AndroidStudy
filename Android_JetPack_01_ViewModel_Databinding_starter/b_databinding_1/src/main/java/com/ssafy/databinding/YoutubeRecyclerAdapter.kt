package com.ssafy.databinding

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.databinding.databinding.ListItemBinding

class YoutubeRecyclerAdapter(val context: Context, private val list: ArrayList<YoutubeDto>) :
    RecyclerView.Adapter<YoutubeRecyclerAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        //binding을 객체로 받아 데이터와 연결시켜준다.
        fun bind(item: YoutubeDto) {
            binding.apply {
                youtubeDto = item
                activity = context as MainActivity
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//            //데이터 바인딩을 이용해 뷰와 연결한다.
        var listItemBinding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(listItemBinding)

//        val listItemBinding2: ListItemBinding = DataBindingUtil.inflate(
//            LayoutInflater.from(parent.context), R.layout.list_item, parent, false)
//        return ViewHolder(listItemBinding2)

//        return ViewHolder(
//            DataBindingUtil.inflate(
//                LayoutInflater.from(parent.context), R.layout.list_item, parent, false
//            )
//        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dto = list[position]
        holder.apply {
            bind(dto)
            itemView.tag = dto
        }
    }
    override fun getItemCount() = list.size

}

