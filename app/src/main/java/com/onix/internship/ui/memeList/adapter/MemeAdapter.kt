package com.onix.internship.ui.memeList.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.onix.internship.databinding.MemeItemBinding
import com.onix.internship.objects.local.MemeData

class MemeHolder(
    private val binding: MemeItemBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item : MemeData) {
         binding.data = item
    }
}

class MemeAdapter : PagingDataAdapter<MemeData, MemeHolder>(MEME_DIFF_CALLBACK){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemeHolder =
        MemeHolder(
            MemeItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
        )

    override fun onBindViewHolder(holder: MemeHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }

    companion object {
        private val MEME_DIFF_CALLBACK = object : DiffUtil.ItemCallback<MemeData>() {
            override fun areItemsTheSame(oldItem: MemeData, newItem: MemeData): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: MemeData, newItem: MemeData): Boolean =
                oldItem == newItem
        }
    }

}