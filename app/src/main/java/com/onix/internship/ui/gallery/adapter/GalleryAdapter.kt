package com.onix.internship.ui.gallery.adapter

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.onix.internship.R
import com.onix.internship.arch.ui.recycler.BaseRecyclerAdapter
import com.onix.internship.arch.ui.recycler.BaseViewHolder
import com.onix.internship.ui.gallery.adapter.model.GalleryAdapterModel

class GalleryAdapter : BaseRecyclerAdapter<GalleryAdapterModel>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<GalleryAdapterModel, out ViewDataBinding> {
        return GalleryViewHolder(
            binding = inflate(parent, R.layout.item_gallery)
        )
    }
}