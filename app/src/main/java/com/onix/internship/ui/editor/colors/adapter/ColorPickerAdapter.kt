package com.onix.internship.ui.editor.colors.adapter

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.onix.internship.R
import com.onix.internship.arch.ui.recycler.BaseRecyclerAdapter
import com.onix.internship.arch.ui.recycler.BaseViewHolder
import com.onix.internship.ui.editor.colors.ColorPickerModel

class ColorPickerAdapter(
    private val pickerModel: ColorPickerModel,
    private val presenter: ColorPickerAdapterPresenter
) : BaseRecyclerAdapter<Int>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<Int, out ViewDataBinding> {
        return ColorViewHolder(
            binding = inflate(parent, R.layout.item_color_picker),
            pickerModel = pickerModel,
            presenter = presenter
        )
    }
}