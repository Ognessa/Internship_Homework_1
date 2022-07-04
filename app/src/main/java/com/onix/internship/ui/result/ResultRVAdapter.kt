package com.onix.internship.ui.result

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.onix.internship.R
import com.onix.internship.arch.adapter.BaseRecyclerAdapter
import com.onix.internship.databinding.ResultItemBinding

class ResultRVAdapter  : BaseRecyclerAdapter<ResultRVAdapter.ResultViewHoder, String>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHoder {
        return from(parent)
    }

    override fun onBindViewHolder(holder: ResultViewHoder, position: Int) {
        holder.bind(adapterItems[position])
    }

    class ResultViewHoder(val binding: ResultItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(it : String){
            binding.tvValue.text = it
        }
    }

    companion object{
        fun from(parent: ViewGroup) : ResultViewHoder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.result_item, parent, false)
            val binding = ResultItemBinding.bind(view)
            return ResultViewHoder(binding)
        }
    }

}