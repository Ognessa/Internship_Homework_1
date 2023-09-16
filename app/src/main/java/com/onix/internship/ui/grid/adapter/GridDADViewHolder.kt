package com.onix.internship.ui.grid.adapter

import androidx.databinding.ViewDataBinding
import com.onix.internship.arch.ui.recycler.BaseViewHolder
import com.onix.internship.databinding.ItemGridDragAndDropBinding
import com.onix.internship.ui.grid.adapter.model.GridDadModel

class GridDADViewHolder(
    binding: ViewDataBinding
) : BaseViewHolder<GridDadModel, ItemGridDragAndDropBinding>(binding) {
    override fun bindView(position: Int) {
        binding.model = item
    }
}