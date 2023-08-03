package com.onix.internship.ui.gallery.player

import android.media.MediaPlayer
import com.onix.internship.ui.gallery.adapter.model.GalleryAdapterModel

interface GalleryMediaPlayer {

    val player: MediaPlayer

    fun playPause(item: GalleryAdapterModel)

    fun pause() {}
}