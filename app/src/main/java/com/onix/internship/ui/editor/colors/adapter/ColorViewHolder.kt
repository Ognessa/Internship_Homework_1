package com.onix.internship.ui.editor.colors.adapter

import androidx.databinding.ViewDataBinding
import com.onix.internship.arch.ui.recycler.BaseViewHolder
import com.onix.internship.databinding.ItemColorPickerBinding
import com.onix.internship.ui.editor.colors.ColorPickerModel

class ColorViewHolder(
    binding: ViewDataBinding,
    private val pickerModel: ColorPickerModel,
    private val presenter: ColorPickerAdapterPresenter
) : BaseViewHolder<Int, ItemColorPickerBinding>(binding) {

    override fun bindView(position: Int) {
        binding.color = item
        binding.pickerModel = pickerModel
        binding.presenter = presenter
    }
}