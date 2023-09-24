package com.onix.internship.ui.editor.colors

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.onix.internship.arch.controller.BaseViewModel
import com.onix.internship.ui.editor.colors.adapter.ColorPickerAdapterPresenter
import com.onix.internship.ui.editor.colors.adapter.model.ColorModel

class ColorPickerViewModel(
    private val selectedColor: Int
) : BaseViewModel(), ColorPickerAdapterPresenter {

    val model = ColorPickerModel()

    private val _updateColor = MutableLiveData<Int>()
    val updateColor: LiveData<Int> = _updateColor

    init {
        model.selectedColor.set(selectedColor)
    }

    override fun onColorSelected(color: ColorModel) {
        model.selectedColor.set(color.color)
        _updateColor.value = model.selectedColor.get()
    }
}