package com.brmlab.shophelp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.brmlab.shophelp.databinding.PersonListBinding
import com.brmlab.shophelp.model.PersonResult

class PersonListAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var dataList = emptyList<PersonResult>()

    fun newList(list: List<PersonResult>){
        this.dataList = list
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PersonViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return dataList.count()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is PersonViewHolder){
            holder.bind(dataList[position])
        }
    }

    private class PersonViewHolder(private val binding: PersonListBinding):
            RecyclerView.ViewHolder(binding.root){
        fun bind(model: PersonResult){
            binding.model = model
            binding.executePendingBindings()
        }

        companion object{
            fun from (view: ViewGroup): PersonViewHolder{
                val binding = PersonListBinding.inflate(LayoutInflater.from(view.context), view, false)
                return PersonViewHolder(binding)
            }
        }
    }
}