package com.onix.internship.ui.avatar

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.onix.internship.R
import com.onix.internship.arch.controller.BaseViewModel
import com.onix.internship.arch.ui.fragment.BaseFragment
import com.onix.internship.arch.ui.view.ktx.loadImage
import com.onix.internship.databinding.FragmentAvatarEditorBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class AvatarEditorFragment :
    BaseFragment<FragmentAvatarEditorBinding>(R.layout.fragment_avatar_editor) {

    private val viewModel: AvatarEditorViewModel by viewModel()

//    private val navArgs: AvatarEditorFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.image.setImage(R.drawable.logo)
    }

    override fun getViewModel(): BaseViewModel = viewModel
}