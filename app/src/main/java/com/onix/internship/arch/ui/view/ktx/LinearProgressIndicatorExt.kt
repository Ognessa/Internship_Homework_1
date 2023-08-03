package com.onix.internship.arch.ui.view.ktx

import androidx.databinding.BindingAdapter
import com.google.android.material.progressindicator.LinearProgressIndicator

@BindingAdapter("allTime", "currentTime")
fun LinearProgressIndicator.setAudioProgress(allTime: Long?, currentTime: Long?) {
    if (allTime != null && currentTime != null) {
        this.max = allTime.toInt()
        this.progress = currentTime.toInt()
    }
}