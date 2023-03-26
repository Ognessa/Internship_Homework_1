package com.onix.internship.ui.gallery.adapter.model

import android.app.Activity
import android.media.MediaPlayer
import android.net.Uri
import com.onix.internship.domain.converter.Mapper
import java.io.File

object GalleryMapper {

    class UrlToGalleryModel(private val activity: Activity) :
        Mapper<String, GalleryAdapterModel>() {

        override fun map(from: String): GalleryAdapterModel {
            return GalleryAdapterModel(
                url = from,
                videoLength = if (File(from).isVideo()) getDuration(from) else null
            )
        }

        private fun getDuration(uri: String): String {
            return try {
                val mp = MediaPlayer.create(activity, Uri.parse(uri))
                val duration = mp.duration
                mp.release()
                formatDuration(duration.toLong())
            } catch (e: Exception) {
                e.printStackTrace()
                "xx:xx"
            }
        }

        private fun formatDuration(duration: Long): String {
            val minutes = duration / 60000
            val seconds = (duration - minutes * 60000) / 1000
            return "$minutes:${seconds}"
        }

        private fun File.isVideo(): Boolean {
            return when (this.extension) {
                "mp4", "avi", "mov" -> true
                else -> false
            }
        }
    }

}