package com.onix.internship.ui.popup

import android.content.Context
import android.view.MenuItem
import android.view.View
import android.widget.PopupWindow
import com.onix.internship.R
import com.onix.internship.arch.adapter.BaseViewHolder
import com.onix.internship.databinding.ItemListPopupWindowBinding

class PopupViewHolder(
    binding: ItemListPopupWindowBinding,
    private val context: Context,
    private val lastItem: MenuItem,
    private val clickListener: PopupClickListener,
    private val popupWindow: PopupWindow
) : BaseViewHolder<MenuItem, ItemListPopupWindowBinding>(binding) {
    override fun bindView(position: Int) {
        binding.menuItem = item

        val deleteText = context.resources.getText(R.string.all_delete_label)
        if (item.title == deleteText) {
            val color = context.resources.getColor(R.color.colorRed, null)
            binding.popupItemOptionTitle.setTextColor(color)
            binding.popupItemOptionIcon.setColorFilter(color)
        }

        if (item.itemId == lastItem.itemId) {
            binding.popupItemDivider.visibility = View.GONE
        }

        binding.root.setOnClickListener {
            clickListener.onItemClickListener(item.itemId, popupWindow)
        }

        binding.executePendingBindings()
    }
}