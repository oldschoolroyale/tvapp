package com.brmlab.shophelp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.brmlab.shophelp.databinding.ListItemBinding
import com.brmlab.shophelp.model.PopularResult

class MovieListAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var dataList = emptyList<PopularResult>()

    fun newList(list: List<PopularResult>){
        this.dataList = list
        notifyDataSetChanged()
    }

    private class ShopViewHolder(private val binding: ListItemBinding):
        RecyclerView.ViewHolder(binding.root){
        fun bind(model: PopularResult){
            binding.model = model
            binding.executePendingBindings()
        }

        companion object{
            fun from(view: ViewGroup): ShopViewHolder{
                val binding = ListItemBinding.inflate(LayoutInflater.from(view.context), view, false)
                return ShopViewHolder(binding)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ShopViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return dataList.count()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ShopViewHolder){
            holder.bind(dataList[position])
        }
    }
}