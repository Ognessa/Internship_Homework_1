package com.onix.internship.ui.gallery.player

import android.media.MediaPlayer
import android.util.Log
import com.onix.internship.ui.gallery.adapter.model.GalleryAdapterModel
import java.util.Timer
import java.util.TimerTask

class GalleryMediaPlayerImpl : GalleryMediaPlayer {
    private var currentRecording: GalleryAdapterModel? = null
    override var player = MediaPlayer()

    private var timer: Timer? = null

    init {
        setFinishPlayingListener()
        setupErrorListener()
        setupOnPrepared()
//        setupOnPrepared()
    }

    private fun setNewRecording(item: GalleryAdapterModel) {
        try {
            currentRecording?.let {
                stop()
                it.currentAudioTimeSeconds.set(0)
            }

            timer?.cancel()
            timer = null

            currentRecording = item
            player.setDataSource(item.url)
            player.prepare()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun playPause(item: GalleryAdapterModel) {
        val currentTime = currentRecording?.currentAudioTimeSeconds?.get() ?: 0L
        if (currentTime == 0L || item != currentRecording) {
            setNewRecording(item)
        } else {
            if (currentRecording?.playing?.get() == false && !player.isPlaying)
                play()
            else
                pause()
        }
    }

    private fun play() {
        currentRecording?.let {
            it.playing.set(true)
            player.start()
            createTimer()
        }
    }

    private fun setFinishPlayingListener() {
        player.setOnCompletionListener {
            currentRecording?.let {
                it.playing.set(false)
                it.currentAudioTimeSeconds.set(0)
            }
            player.reset()
            timer?.cancel()
        }
    }

    private fun setupErrorListener() {
        player.setOnErrorListener { mp, what, extra ->
            Log.d("DEBUG", "Error: ${what} -> ${extra}")
            stop()
            true
        }
    }

    private fun setBufferingListener() {
        //This is for buffering data from server
        player.setOnBufferingUpdateListener { mp, percent ->
            Log.d("DEBUG", "Percent: ${percent}")
        }
    }

    private fun stop() {
        currentRecording?.let {
            it.playing.set(false)
            player.stop()
            player.reset()
            timer?.cancel()
        }
    }

    override fun pause() {
        currentRecording?.let {
            it.playing.set(false)
            player.pause()
            timer?.cancel()
        }
    }

    private fun setupOnPrepared() {
        player.setOnPreparedListener {
            play()
        }
    }

    //TODO time progress dont go to the end
    private fun createTimer(): Timer {
        timer = Timer().apply {
            scheduleAtFixedRate(object : TimerTask() {
                override fun run() {
                    currentRecording?.let {
                        when {
                            !player.isPlaying && it.currentAudioTimeSeconds.get() != it.audioLengthSeconds.get() -> {
                                it.currentAudioTimeSeconds.set(0)
                                cancel()
                            }

                            else -> {
                                it.currentAudioTimeSeconds.set(player.currentPosition.toLong())
                            }
                        }
                    }
                }
            }, 0, 100)
        }
        return timer!!
    }
}