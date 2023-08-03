package com.onix.internship.ui.gallery.adapter.model

import android.media.MediaMetadataRetriever
import androidx.databinding.ObservableLong
import com.onix.internship.domain.converter.Mapper

object GalleryMapper {

    class UrlToGalleryModel : Mapper<String, GalleryAdapterModel>() {
        override fun map(from: String): GalleryAdapterModel {
            return GalleryAdapterModel(
                url = from,
                audioLengthSeconds = ObservableLong(getDuration(from))
            )
        }

        private fun getDuration(uri: String): Long {
            return try {
                val mmr = MediaMetadataRetriever()
                mmr.setDataSource(uri)
                val durationStr = mmr.extractMetadata(
                    MediaMetadataRetriever.METADATA_KEY_DURATION
                ).orEmpty()
                durationStr.toLong()
            } catch (e: Exception) {
                e.printStackTrace()
                0
            }
        }
    }

}