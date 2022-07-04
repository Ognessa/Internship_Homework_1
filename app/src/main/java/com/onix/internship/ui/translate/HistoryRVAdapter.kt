package com.onix.internship.ui.translate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.onix.internship.R
import com.onix.internship.arch.adapter.BaseRecyclerAdapter
import com.onix.internship.data.HistoryItem
import com.onix.internship.databinding.HistoryItemBinding

class HistoryRVAdapter : BaseRecyclerAdapter<HistoryRVAdapter.HistoryViewHolder, HistoryItem>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        return from(parent)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(adapterItems[position])
    }

    class HistoryViewHolder(val binding: HistoryItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(it : HistoryItem){
            binding.tvDictTitle.text = it.dictTitle
            binding.tvHistoryItem.text ="${it.key} - ${it.value}"
        }
    }

    companion object{
        fun from(parent: ViewGroup) : HistoryViewHolder{
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.history_item, parent, false)
            val binding = HistoryItemBinding.bind(view)
            return HistoryViewHolder(binding)
        }
    }
}