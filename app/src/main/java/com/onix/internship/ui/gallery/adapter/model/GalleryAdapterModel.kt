package com.onix.internship.ui.gallery.adapter.model

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableLong

data class GalleryAdapterModel(
    val url: String,
    val audioLengthSeconds: ObservableLong = ObservableLong(0),
    val currentAudioTimeSeconds: ObservableLong = ObservableLong(0),
    val playing: ObservableBoolean = ObservableBoolean(false)
)