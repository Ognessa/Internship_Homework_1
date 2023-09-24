package com.onix.internship.ui.editor.colors.adapter

import com.onix.internship.ui.editor.colors.adapter.model.ColorModel

interface ColorPickerAdapterPresenter {

    fun onColorSelected(color: ColorModel)
}