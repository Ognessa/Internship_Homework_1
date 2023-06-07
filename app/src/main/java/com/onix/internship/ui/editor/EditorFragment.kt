package com.onix.internship.ui.editor

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import androidx.fragment.app.setFragmentResultListener
import com.onix.internship.R
import com.onix.internship.arch.controller.BaseViewModel
import com.onix.internship.arch.ui.fragment.BaseFragment
import com.onix.internship.databinding.FragmentEditorBinding
import com.onix.internship.ui.editor.colors.ColorPickerDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditorFragment : BaseFragment<FragmentEditorBinding>(R.layout.fragment_editor) {

    private val viewModel: EditorViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.model = viewModel.model
        binding.presenter = viewModel
        setupStartColor()
        listenFragmentResult()
    }

    override fun setObservers() {
        super.setObservers()
        viewModel.image.observe(viewLifecycleOwner, ::handleImageLoaded)
    }

    private fun handleImageLoaded(bitmap: Bitmap) {
        binding.imageEditor.setImageBitmap(bitmap)
    }

    private fun setupStartColor() {
        viewModel.model.selectedColor.set(resources.getColor(R.color.blue, null))
    }

    private fun listenFragmentResult() {
        setFragmentResultListener(ColorPickerDialogFragment.SELECTED_COLOR) { requestKey, bundle ->
            viewModel.model.selectedColor.set(bundle.getInt(requestKey))
        }
    }

    override fun getViewModel(): BaseViewModel = viewModel
}