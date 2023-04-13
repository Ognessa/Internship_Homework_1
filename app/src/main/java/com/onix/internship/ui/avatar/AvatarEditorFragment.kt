package com.onix.internship.ui.avatar

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.onix.internship.R
import com.onix.internship.arch.controller.BaseViewModel
import com.onix.internship.arch.router.command.NavDirection
import com.onix.internship.arch.ui.fragment.BaseFragment
import com.onix.internship.databinding.FragmentAvatarEditorBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class AvatarEditorFragment :
    BaseFragment<FragmentAvatarEditorBinding>(R.layout.fragment_avatar_editor) {

    private val viewModel: AvatarEditorViewModel by viewModel()

    private val navArgs: AvatarEditorFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadBitmap(navArgs.url)
        setupSelectBtn()
    }

    override fun setObservers() {
        super.setObservers()
        viewModel.image.observe(viewLifecycleOwner, ::handleBitmap)
    }

    private fun handleBitmap(bitmap: Bitmap) {
        binding.image.setImage(bitmap)
    }

    private fun setupSelectBtn() {
        binding.selectPhoto.setOnClickListener {
            val bitmap = binding.image.cropImage()
            val url = viewModel.saveBitmap(bitmap)
            if (url != null) {
                navigate(
                    NavDirection.Direction(
                        AvatarEditorFragmentDirections.actionAvatarEditorFragmentToCropResultFragment(
                            url = url
                        )
                    )
                )
            }
        }
    }

    override fun getViewModel(): BaseViewModel = viewModel
}