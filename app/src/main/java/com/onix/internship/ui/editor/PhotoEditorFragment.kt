package com.onix.internship.ui.editor

import android.os.Bundle
import android.view.View
import com.onix.internship.R
import com.onix.internship.arch.BaseFragment
import com.onix.internship.data.repository.ImageRepository
import com.onix.internship.databinding.PhotoEditorFragmentBinding
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class PhotoEditorFragment : BaseFragment<PhotoEditorFragmentBinding>(R.layout.photo_editor_fragment) {

    override val viewModel: PhotoEditorViewModel by viewModel()
    private val imageRepository: ImageRepository by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ivEditImage.setImageBitmap(imageRepository.getImage())
    }

}