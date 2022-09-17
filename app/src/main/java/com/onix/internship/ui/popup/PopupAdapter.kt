package com.onix.internship.ui.popup

import android.content.Context
import android.graphics.Color
import android.view.*
import android.widget.BaseAdapter
import androidx.annotation.MenuRes
import androidx.appcompat.view.menu.MenuBuilder
import androidx.core.view.children
import com.onix.internship.databinding.ItemPopupListBinding

class PopupViewHolder(
    val binding: ItemPopupListBinding,
) {
    fun bind(item: MenuItem): View {
        binding.item = item

        if (item.title == "Shop") {
            binding.actionName.setTextColor(Color.RED)
            binding.icon.setColorFilter(Color.RED)
        }

        binding.executePendingBindings()
        return binding.root
    }
}

class PopupAdapter(
    private val context: Context,
    @MenuRes menuRes: Int
) : BaseAdapter() {

    private var list: List<MenuItem> = listOf()

    init {
        //get menu items list from menu resource and set it to adapter's list
        val mMenu = MenuBuilder(context)
        MenuInflater(context).inflate(menuRes, mMenu)
        list = mMenu.children.toList()
    }

    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): MenuItem {
        return list[position]
    }

    //return menu item's id
    override fun getItemId(position: Int): Long {
        return list[position].itemId.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val item = getItem(position)
        return from(parent, context).bind(item)
    }

    companion object {
        fun from(parent: ViewGroup?, context: Context): PopupViewHolder {
            val inflater = LayoutInflater.from(context)
            val binding = ItemPopupListBinding.inflate(inflater, parent, false)
            return PopupViewHolder(binding)
        }
    }
}