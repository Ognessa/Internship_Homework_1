package com.onix.internship.ui.gallery.adapter

import com.onix.internship.ui.gallery.adapter.model.GalleryAdapterModel

interface GalleryAdapterPresenter {
    fun onPlayPressed(model: GalleryAdapterModel)
}