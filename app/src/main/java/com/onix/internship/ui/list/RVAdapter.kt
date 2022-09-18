package com.onix.internship.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.onix.internship.R
import com.onix.internship.arch.adapter.BaseRecyclerListAdapter
import com.onix.internship.arch.adapter.BaseViewHolder
import com.onix.internship.databinding.ItemBinding

class Holder(
    binding: ItemBinding
) : BaseViewHolder<String, ItemBinding>(binding) {

    override fun bindView(position: Int) {
        binding.text.text = item
    }
}

class RVAdapter : BaseRecyclerListAdapter<String>(DIFF_UTIL) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<String, out ViewDataBinding> {
        return from(parent)
    }

    companion object {
        private fun from(parent: ViewGroup): Holder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.item, parent, false)
            val binding = ItemBinding.bind(view)
            return Holder(binding)
        }

        val DIFF_UTIL = Callback()
    }
}

class Callback : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

}