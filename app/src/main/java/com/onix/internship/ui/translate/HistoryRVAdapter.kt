package com.onix.internship.ui.translate

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.onix.internship.R
import com.onix.internship.arch.adapter.BaseRecyclerAdapter

class HistoryViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
    val tvHistoryItem = itemView.findViewById<TextView>(R.id.tv_history_item)

    fun bind(it : String){
        tvHistoryItem.text = it
    }
}

class HistoryRVAdapter() : BaseRecyclerAdapter<HistoryViewHolder, String>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.history_item, parent, false)
        return HistoryViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(adapterItems[position])
    }

}