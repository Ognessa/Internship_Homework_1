package com.onix.internship.ui.gallery.adapter

import androidx.databinding.ViewDataBinding
import com.onix.internship.arch.ui.recycler.BaseViewHolder
import com.onix.internship.databinding.ItemGalleryBinding
import com.onix.internship.ui.gallery.adapter.model.GalleryAdapterModel

class GalleryViewHolder(
    binding: ViewDataBinding,
    private val adapterPresenter: GalleryAdapterPresenter
) : BaseViewHolder<GalleryAdapterModel, ItemGalleryBinding>(binding) {

    override fun bindView(position: Int) {
        binding.model = item
        binding.presenter = adapterPresenter
    }
}