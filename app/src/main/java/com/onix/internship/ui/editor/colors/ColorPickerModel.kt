package com.onix.internship.ui.editor.colors

import android.graphics.Color
import androidx.databinding.ObservableInt

data class ColorPickerModel(
    val selectedColor: ObservableInt = ObservableInt(Color.RED)
)