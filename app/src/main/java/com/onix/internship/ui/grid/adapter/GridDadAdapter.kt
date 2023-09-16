package com.onix.internship.ui.grid.adapter

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.onix.internship.R
import com.onix.internship.arch.ui.recycler.BaseRecyclerAdapter
import com.onix.internship.arch.ui.recycler.BaseViewHolder
import com.onix.internship.ui.grid.adapter.model.GridDadModel

class GridDadAdapter : BaseRecyclerAdapter<GridDadModel>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<GridDadModel, out ViewDataBinding> {
        return GridDADViewHolder(binding = inflate(parent, R.layout.item_grid_drag_and_drop))
    }
}