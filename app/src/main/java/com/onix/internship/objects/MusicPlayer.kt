package com.onix.internship.objects

import android.content.Context
import android.media.MediaPlayer
import com.onix.internship.R

class MusicPlayer(val context: Context) {
    private lateinit var player: MediaPlayer

    fun initPlayer() {
        player = MediaPlayer.create(context, R.raw.illurock)
        player.isLooping = true // Set looping
    }

    fun playMusic(){
        if(!player.isPlaying)
            player.start()
    }

    fun stopMusic(){
        if(player.isPlaying){
            player.stop()
            player.prepare()
        }
    }

    fun musicStatus() : Boolean{
        return player.isPlaying
    }

}