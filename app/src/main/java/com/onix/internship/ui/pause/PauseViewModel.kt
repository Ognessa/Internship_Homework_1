package com.onix.internship.ui.pause

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.onix.internship.arch.BaseViewModel
import com.onix.internship.objects.MusicPlayer
import com.onix.internship.objects.Scenario

class PauseViewModel(private val scenario: Scenario, val player: MusicPlayer) : BaseViewModel() {

    private val _musicStatus = MutableLiveData(player.musicStatus())
    val musicStatus : LiveData<Boolean> get() = _musicStatus

    fun stepBack(){
        scenario.currentLine--
    }

    fun playOrStopMusic(){
        if(player.musicStatus())
            player.stopMusic()
        else
            player.playMusic()

        _musicStatus.postValue(player.musicStatus())
    }

    fun clearData(){
        scenario.currentLine = -1
        scenario.currentLabel = scenario.scenario.first().title
    }
}