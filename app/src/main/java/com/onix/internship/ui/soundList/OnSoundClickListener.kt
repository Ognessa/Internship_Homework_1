package com.onix.internship.ui.soundList

import com.onix.internship.objects.localObjects.RecordingData

interface OnSoundClickListener {
    fun playSound(item : RecordingData){}
    fun soundSelected(item : RecordingData){}
}