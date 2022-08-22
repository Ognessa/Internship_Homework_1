package com.onix.internship.players

import android.media.MediaPlayer
import com.onix.internship.objects.localObjects.RecordingData

class RecordingsPlayer {

    private var currentResording = RecordingData()

    private val player = MediaPlayer()

    fun setNewRecording(item : RecordingData){
        if(currentResording != item){
            if(currentResording.isPlaying.get() && player.isPlaying)
                stop()

            currentResording = item
        }
    }

    fun playPause(){
        if(!currentResording.isPlaying.get() && !player.isPlaying)
            play()
        else
            stop()
    }

    private fun play(){
        currentResording.isPlaying.set(true)
        player.setDataSource(currentResording.file)
        player.prepare()
        player.start()
    }

    private fun stop(){
        currentResording.isPlaying.set(false)
        player.stop()
        player.reset()
    }

}