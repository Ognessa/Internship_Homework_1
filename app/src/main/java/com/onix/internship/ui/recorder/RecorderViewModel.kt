package com.onix.internship.ui.recorder

import android.content.Context
import android.media.MediaMetadataRetriever
import android.util.Log
import androidx.databinding.ObservableLong
import com.onix.internship.arch.controller.BaseViewModel
import com.onix.internship.ui.gallery.adapter.model.GalleryAdapterModel
import com.onix.internship.ui.gallery.player.GalleryMediaPlayer
import com.onix.internship.ui.gallery.recorder.AudioRecorder
import java.io.File

class RecorderViewModel(
    private val player: GalleryMediaPlayer,
    private val recorder: AudioRecorder,
    private val context: Context
) : BaseViewModel(), RecorderPresenter {

    private var audioFile: File? = null

    val model = RecorderModel()

    init {
        recorder.setRecorderListener {
            model.timeRecordingMessage.set(it)
        }
    }

    private fun createTempFile(): File {
        val directory = File(context.filesDir, AUDIO_DIRECTORY)

        if (!directory.exists()) {
            directory.mkdir()
        }

        return File(directory, AUDIO_NAME)
    }

    companion object {
        const val AUDIO_DIRECTORY = "audio"
        const val AUDIO_NAME = "voiceMessage.m4a"
    }

    override fun onStartRecording() {
        audioFile?.delete()
        audioFile = createTempFile()
        Log.d("DEBUG", "Path: ${audioFile?.absolutePath}")
        recorder.start(audioFile!!)
    }

    override fun onStopRecording() {
        recorder.stop()
    }

    override fun onPlayPressed() {
        audioFile?.let {
            player.playPause(
                GalleryAdapterModel(
                    url = it.path,
                    audioLengthSeconds = ObservableLong(getDuration(it.path))
                )
            )
        }
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