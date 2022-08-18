package com.onix.internship.ui.moreInfo

import com.onix.internship.arch.BaseViewModel
import com.onix.internship.players.RecordingsPlayer

class MoreInfoViewModel(
    val model: MoreInfoModel,
    private val recordingsPlayer: RecordingsPlayer
) : BaseViewModel(){

    fun playRecording(){
        recordingsPlayer.setNewRecording(model.recordingData)
        recordingsPlayer.playPause()
    }

}