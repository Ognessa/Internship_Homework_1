package com.onix.internship.ui.gallery.adapter.model

import androidx.databinding.ObservableBoolean

data class GalleryAdapterModel(
    val url: String,
    val videoLength: String? = null,
    val selected: ObservableBoolean = ObservableBoolean(false)
)