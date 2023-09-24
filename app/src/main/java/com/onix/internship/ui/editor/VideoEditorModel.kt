package com.onix.internship.ui.editor

import androidx.databinding.ObservableField

data class VideoEditorModel(
    val selectedMode: ObservableField<VideoEditorModeTypes> = ObservableField(VideoEditorModeTypes.NONE)
)
