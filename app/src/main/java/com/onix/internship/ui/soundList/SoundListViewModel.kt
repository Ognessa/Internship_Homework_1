package com.onix.internship.ui.soundList

import com.onix.internship.arch.BaseViewModel
import com.onix.internship.arch.lifecycle.SingleLiveEvent
import com.onix.internship.data.repository.ApiDataRepository
import com.onix.internship.objects.localObjects.RecordingData
import com.onix.internship.players.RecordingsPlayer
import com.onix.internship.ui.moreInfo.MoreInfoModel

class SoundListViewModel(
    private val apiDataRepository: ApiDataRepository,
    private val moreInfoModel: MoreInfoModel,
    private val recordingsPlayer: RecordingsPlayer
) : BaseViewModel(){

    val navigate = SingleLiveEvent<Unit>()

    fun getRecordingsList(): List<RecordingData> {
        return apiDataRepository.apiData.recordings
    }

    fun checkListIsEmpty(): Boolean {
        return apiDataRepository.apiData.recordings.isEmpty()
    }

    fun selectRecord(recordingData: RecordingData){
        moreInfoModel.recordingData = recordingData
        navigate.postValue(Unit)
    }

    fun setNewRecording(item : RecordingData){
        recordingsPlayer.setNewRecording(item)
    }

    fun playPauseRecording(){
        recordingsPlayer.playPause()
    }

}