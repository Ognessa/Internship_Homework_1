package com.onix.internship.ui.editor

import android.graphics.Color
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt

data class EditorModel(
    val selectedMode: ObservableField<EditorModeTypes> = ObservableField(EditorModeTypes.NONE),
    val selectedColor: ObservableInt = ObservableInt(Color.RED),
    val showEditorControls: ObservableBoolean = ObservableBoolean(true)
)