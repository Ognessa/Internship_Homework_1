package com.onix.internship.ui.editor.colors

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.onix.internship.arch.controller.BaseViewModel
import com.onix.internship.ui.editor.colors.adapter.ColorPickerAdapterPresenter

class ColorPickerViewModel(
    private val selectedColor: Int
) : BaseViewModel(), ColorPickerAdapterPresenter, ColorPickerPresenter {

    val model = ColorPickerModel()

    private val _updateColor = MutableLiveData<Int>()
    val updateColor: LiveData<Int> = _updateColor

    init {
        model.selectedColor.set(selectedColor)
    }

    override fun onColorSelected(color: Int) {
        model.selectedColor.set(color)
    }

    override fun onSelectPressed() {
        _updateColor.value = model.selectedColor.get()
    }
}