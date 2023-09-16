package com.onix.internship.ui.linear.adapter

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.onix.internship.R
import com.onix.internship.arch.ui.recycler.BaseDiffRecyclerAdapter
import com.onix.internship.arch.ui.recycler.BaseViewHolder
import com.onix.internship.ui.linear.adapter.model.LinearDADModel

class LinearDADAdapter : BaseDiffRecyclerAdapter<LinearDADModel>() {

    override fun compare(oldItem: LinearDADModel, newItem: LinearDADModel): Boolean {
        return oldItem == newItem
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<LinearDADModel, out ViewDataBinding> {
        return DragAndDropViewHolder(binding = inflate(parent, R.layout.item_liner_drag_and_drop))
    }
}