package com.onix.internship.ui.editor.colors

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.navArgs
import com.onix.internship.R
import com.onix.internship.arch.controller.BaseViewModel
import com.onix.internship.arch.router.command.Command
import com.onix.internship.arch.ui.dialog.BaseBottomDialogFragment
import com.onix.internship.arch.ui.view.model.TextProvider
import com.onix.internship.databinding.DialogColorPickerBinding
import com.onix.internship.ui.editor.colors.adapter.ColorPickerAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ColorPickerDialogFragment :
    BaseBottomDialogFragment<DialogColorPickerBinding>(R.layout.dialog_color_picker) {

    private val navArgs: ColorPickerDialogFragmentArgs by navArgs()

    private val viewModel: ColorPickerViewModel by viewModel {
        parametersOf(navArgs.selectedColor)
    }

    private val colorMapper by lazy { ColorModelMapper.ColorIntToColorModel(requireContext()) }

    private val colorAdapter by lazy {
        ColorPickerAdapter(
            pickerModel = viewModel.model,
            presenter = viewModel
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.model = viewModel.model
        setupAdapter()
    }

    override fun setObservers() {
        super.setObservers()
        viewModel.updateColor.observe(viewLifecycleOwner, ::handleColorUpdate)
    }

    private fun setupAdapter() {
        val colorList = resources.getIntArray(R.array.editorColor).toList()

        binding.colorsList.adapter = colorAdapter
        colorAdapter.items = colorMapper.map(colorList)
    }

    private fun handleColorUpdate(color: Int) {
        setFragmentResult(SELECTED_COLOR, bundleOf(SELECTED_COLOR to color))
        navigate(Command.Back)
    }

    override fun getViewModel(): BaseViewModel = viewModel

    companion object {
        const val SELECTED_COLOR = "selectedColor"
    }
}