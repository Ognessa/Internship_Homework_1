package com.onix.internship.ui.editor

import android.os.Bundle
import android.view.View
import com.onix.internship.R
import com.onix.internship.arch.BaseFragment
import com.onix.internship.databinding.PhotoEditorFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class PhotoEditorFragment : BaseFragment<PhotoEditorFragmentBinding>(R.layout.photo_editor_fragment) {

    override val viewModel: PhotoEditorViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ivEditImage.setImageBitmap(viewModel.getImage())
    }

}