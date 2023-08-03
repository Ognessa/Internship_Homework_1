package com.onix.internship.arch.ui.view.ktx

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.onix.internship.R
import com.onix.internship.arch.ktx.formatDuration
import com.onix.internship.arch.ktx.getString
import com.onix.internship.arch.ui.view.model.TextProvider

@BindingAdapter("setText")
fun TextView.setText(textProvider: TextProvider?) {
    textProvider?.let { text = textProvider.getString(context) }
}

@BindingAdapter("currentTime", "allTime", requireAll = false)
fun TextView.setAudioTimer(currentTime: Long?, allTime: Long?) {
    if (currentTime != null && allTime != null) {
        val pattern = resources.getString(R.string.audio_timer_pattern)
        text = String.format(pattern, currentTime.formatDuration(), allTime.formatDuration())
    }
}

@BindingAdapter("recorderTime")
fun TextView.setRecorderTime(recorderTime: Long?) {
    if (recorderTime != null) {
        text = recorderTime.formatDuration()
    }
}