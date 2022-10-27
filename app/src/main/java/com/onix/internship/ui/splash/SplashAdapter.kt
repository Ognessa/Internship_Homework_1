package com.onix.internship.ui.splash

import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.onix.internship.R
import com.onix.internship.arch.adapter.BaseRecyclerListAdapter
import com.onix.internship.arch.adapter.BaseViewHolder
import com.onix.internship.databinding.SplashItemBinding

class SplashViewHolder(
    binding: SplashItemBinding,
    val itemClick: (View) -> Unit
) : BaseViewHolder<String, SplashItemBinding>(binding) {
    override fun bindView(position: Int) {
        binding.textView.text = item
        binding.root.setOnClickListener { itemClick(it) }
    }
}

class SplashAdapter(
    val itemClick: (View) -> Unit
) : BaseRecyclerListAdapter<String>(SplashDiffUtils()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<String, out ViewDataBinding> {
        return SplashViewHolder(
            binding = inflate(parent, R.layout.splash_item) as SplashItemBinding,
            itemClick = itemClick
        )
    }

}

class SplashDiffUtils : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}