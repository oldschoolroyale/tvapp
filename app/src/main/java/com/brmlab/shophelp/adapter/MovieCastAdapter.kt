package com.brmlab.shophelp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.brmlab.shophelp.databinding.CastLayoutBinding
import com.brmlab.shophelp.databinding.PersonListBinding
import com.brmlab.shophelp.model.Cast
import com.brmlab.shophelp.model.MovieCredModel
import com.brmlab.shophelp.model.PersonResult

class MovieCastAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var dataList = emptyList<Cast>()

    fun newList(list: List<Cast>){
        this.dataList = list
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MovieCastViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return dataList.count()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MovieCastViewHolder){
            holder.bind(dataList[position])
        }
    }

    private class MovieCastViewHolder(private val binding: CastLayoutBinding):
        RecyclerView.ViewHolder(binding.root){
        fun bind(model: Cast){
            binding.model = model
            binding.executePendingBindings()
        }

        companion object{
            fun from (view: ViewGroup): MovieCastViewHolder{
                val binding = CastLayoutBinding.inflate(LayoutInflater.from(view.context), view, false)
                return MovieCastViewHolder(binding)
            }
        }
    }
}