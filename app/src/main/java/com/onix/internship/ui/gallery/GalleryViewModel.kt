package com.onix.internship.ui.gallery

import android.content.ContentResolver
import android.provider.MediaStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.onix.internship.arch.controller.BaseViewModel
import com.onix.internship.arch.router.command.NavDirection
import com.onix.internship.ui.gallery.adapter.GalleryAdapterPresenter
import com.onix.internship.ui.gallery.adapter.model.GalleryAdapterModel
import com.onix.internship.ui.gallery.adapter.model.GalleryMapper
import com.onix.internship.ui.gallery.player.GalleryMediaPlayer

class GalleryViewModel(
    private val contentResolver: ContentResolver,
    private val audioPlayer: GalleryMediaPlayer
) : BaseViewModel(), GalleryPresenter, GalleryAdapterPresenter {

    private val mapper = GalleryMapper.UrlToGalleryModel()

    private val _audioList = MutableLiveData<List<GalleryAdapterModel>>()
    val audioList: LiveData<List<GalleryAdapterModel>> = _audioList

    init {
        loadData()
    }

    private fun loadData() {
        val columns = arrayOf(
            MediaStore.Files.FileColumns._ID,
            MediaStore.Files.FileColumns.DATA,
            MediaStore.Files.FileColumns.DATE_ADDED,
            MediaStore.Files.FileColumns.MEDIA_TYPE,
            MediaStore.Files.FileColumns.MIME_TYPE,
            MediaStore.Files.FileColumns.TITLE
        )
        val selection = (MediaStore.Files.FileColumns.MEDIA_TYPE + "="
                + MediaStore.Files.FileColumns.MEDIA_TYPE_AUDIO)
        val queryUri = MediaStore.Files.getContentUri("external")

        val cursor = contentResolver.query(
            queryUri,
            columns,
            selection,
            null,
            MediaStore.Files.FileColumns.DATE_ADDED + " DESC"
        )

        val files = mutableListOf<String>()

        if (cursor != null) {
            val columnIndexData = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
            while (cursor.moveToNext()) {
                val absolutePathOfImage = cursor.getString(columnIndexData)
                files.add(absolutePathOfImage)
            }
        }

        _audioList.value = mapper.map(files)
    }

    override fun onRecordingVoiceMessage() {
        navigate(
            NavDirection.Direction(
                GalleryFragmentDirections.actionSplashFragmentToRecorderFragment()
            )
        )
    }

    override fun onPlayPressed(model: GalleryAdapterModel) {
//        _audioList.value?.forEach {
//            when (model) {
//                it -> it.playing.set(!it.playing.get())
//                else -> it.playing.set(false)
//            }
//        }
        audioPlayer.playPause(model)
    }

    fun pauseAudioPLaying() {
        audioPlayer.pause()
    }
}