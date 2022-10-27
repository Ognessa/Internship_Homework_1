package com.onix.internship.ui.popup

import android.content.Context
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.onix.internship.R
import com.onix.internship.arch.adapter.BaseRecyclerListAdapter
import com.onix.internship.arch.adapter.BaseViewHolder
import com.onix.internship.databinding.ItemListPopupWindowBinding

class PopupAdapter(
    private val context: Context,
    private val lastItem: MenuItem,
    private val clickListener: PopupClickListener,
    private val popupWindow: PopupWindow
) : BaseRecyclerListAdapter<MenuItem>(PopupDiffUtils()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<MenuItem, out ViewDataBinding> {
        return PopupViewHolder(
            binding = inflate(
                parent,
                R.layout.item_list_popup_window
            ) as ItemListPopupWindowBinding,
            context = context,
            lastItem = lastItem,
            clickListener = clickListener,
            popupWindow = popupWindow
        )
    }
}

class PopupDiffUtils : DiffUtil.ItemCallback<MenuItem>() {
    override fun areItemsTheSame(oldItem: MenuItem, newItem: MenuItem): Boolean {
        return oldItem.itemId == newItem.itemId
    }

    override fun areContentsTheSame(oldItem: MenuItem, newItem: MenuItem): Boolean {
        return oldItem == newItem
    }
}