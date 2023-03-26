package com.onix.internship.ui.gallery.adapter

import androidx.databinding.ViewDataBinding
import coil.load
import com.onix.internship.R
import com.onix.internship.arch.ui.recycler.BaseViewHolder
import com.onix.internship.databinding.ItemGalleryBinding
import com.onix.internship.ui.gallery.adapter.model.GalleryAdapterModel

class GalleryViewHolder(
    binding: ViewDataBinding
) : BaseViewHolder<GalleryAdapterModel, ItemGalleryBinding>(binding) {

    override fun bindView(position: Int) {
        binding.model = item
        binding.image.load(item.url) {
            error(R.drawable.placeholder)
        }
        binding.root.setOnClickListener {
            item.selected.set(!item.selected.get())
        }
    }
}