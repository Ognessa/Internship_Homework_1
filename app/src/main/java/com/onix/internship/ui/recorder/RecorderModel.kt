package com.onix.internship.ui.recorder

import androidx.databinding.ObservableLong

data class RecorderModel(
    val timeRecordingMessage: ObservableLong = ObservableLong(0)
)
