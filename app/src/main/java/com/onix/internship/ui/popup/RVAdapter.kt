package com.onix.internship.ui.popup

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListPopupWindow
import android.widget.Toast
import androidx.annotation.MenuRes
import androidx.recyclerview.widget.RecyclerView
import com.onix.internship.R
import com.onix.internship.arch.adapter.BaseRecyclerAdapter
import com.onix.internship.databinding.ItemPopupListBinding
import com.onix.internship.databinding.ListItemBinding

class Holder(
    private val binding: ListItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: String) {
        binding.tv.text = item
        binding.root.setOnClickListener { createPopup(it, R.menu.menu, clickListener) }
    }

    //function that create popup (can be moved to own class)
    private fun createPopup(
        view: View,
        @MenuRes menuRes: Int,
        listener: AdapterView.OnItemClickListener
    ) {
        val listPopupWindow = ListPopupWindow(binding.root.context)

        listPopupWindow.setAdapter(
            PopupAdapter(
                binding.root.context,
                menuRes
            )
        )

        listPopupWindow.apply {
            //customize popup
            anchorView = view
            width = binding.root.context.resources.getDimensionPixelSize(R.dimen.width)
            height = ListPopupWindow.WRAP_CONTENT
            setBackgroundDrawable(
                binding.root.context.resources.getDrawable(
                    R.drawable.popup_background,
                    null
                )
            )
            isModal = true

            //set clicklistener and dismiss popup when menu item is clicked
            setOnItemClickListener{ parent, view, position, id ->
                listener.onItemClick(parent, view, position, id)
                listPopupWindow.dismiss()
            }

            show()
        }
    }

    //Click listener for menu items
    private val clickListener = AdapterView.OnItemClickListener { _, _, _, id ->
        when (id.toInt()) {
            R.id.home -> {
                Toast.makeText(binding.root.context, "Home", Toast.LENGTH_SHORT).show()
            }
            R.id.gallery -> {
                Toast.makeText(binding.root.context, "Gallery", Toast.LENGTH_SHORT).show()
            }
            R.id.shop -> {
                Toast.makeText(binding.root.context, "Shop", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

class RVAdapter : BaseRecyclerAdapter<Holder, String>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return from(parent)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(adapterItems[position])
    }

    companion object {
        fun from(parent: ViewGroup): Holder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.list_item, parent, false)
            val binding = ListItemBinding.bind(view)
            return Holder(binding)
        }
    }
}