package com.onix.internship.ui.linear.adapter

import androidx.databinding.ViewDataBinding
import com.onix.internship.arch.ui.recycler.BaseViewHolder
import com.onix.internship.databinding.ItemLinerDragAndDropBinding
import com.onix.internship.ui.linear.adapter.model.LinearDADModel

class DragAndDropViewHolder(
    binding: ViewDataBinding
) : BaseViewHolder<LinearDADModel, ItemLinerDragAndDropBinding>(binding) {
    override fun bindView(position: Int) {
        binding.model = item
        binding.executePendingBindings()
    }
}