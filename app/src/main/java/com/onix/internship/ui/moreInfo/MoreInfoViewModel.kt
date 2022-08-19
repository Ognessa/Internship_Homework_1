package com.onix.internship.ui.moreInfo

import com.onix.internship.arch.BaseViewModel
import com.onix.internship.data.download.DownloadManager
import com.onix.internship.players.RecordingsPlayer

class MoreInfoViewModel(
    val model: MoreInfoModel,
    private val recordingsPlayer: RecordingsPlayer,
    private val downloadManager: DownloadManager
) : BaseViewModel(){

    fun playRecording(){
        recordingsPlayer.setNewRecording(model.recordingData)
        recordingsPlayer.playPause()
    }

    fun downloadFile(){
        showSnack("Downloading...")
        val url = model.recordingData.file
        val name = model.recordingData.fileName
        downloadManager.downloadRequest(name, url)
    }

}