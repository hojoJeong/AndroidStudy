package com.ssafy.databinding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.databinding.databinding.ListItemBinding

class YoutubeRecyclerAdapter(private val list: ArrayList<YoutubeDto>) :
    RecyclerView.Adapter<YoutubeRecyclerAdapter.ViewHolder>() {

    class ViewHolder( private val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: YoutubeDto) {
            binding.apply {
                youtubeDto = item
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate( LayoutInflater.from(parent.context), R.layout.list_item, parent, false )
        )
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

