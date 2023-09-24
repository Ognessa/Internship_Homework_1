package com.onix.internship.ui.editor

import com.onix.internship.R
import com.onix.internship.arch.controller.BaseViewModel
import com.onix.internship.arch.ui.fragment.BaseFragment
import com.onix.internship.databinding.FragmentVideoEditorBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class VideoEditorFragment :
    BaseFragment<FragmentVideoEditorBinding>(R.layout.fragment_video_editor) {

    private val viewModel: VideoEditorViewModel by viewModel()

    override fun getViewModel(): BaseViewModel = viewModel
}