package com.onix.internship.ui.editor

import android.graphics.Color
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt

data class EditorModel(
    val selectedMode: ObservableField<EditorModeTypes> = ObservableField(EditorModeTypes.RECTANGLE),
    val selectedColor: ObservableInt = ObservableInt(Color.RED)
)