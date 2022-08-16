package com.onix.internship.ui.mainMenu

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.onix.internship.R
import com.onix.internship.arch.BaseFragment
import com.onix.internship.arch.ext.navigate
import com.onix.internship.data.repository.ImageRepository
import com.onix.internship.databinding.MainMenuFragmentBinding
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.IOException


class MainMenuFragment : BaseFragment<MainMenuFragmentBinding>(R.layout.main_menu_fragment) {

    override val viewModel: MainMenuViewModel by viewModel()
    private val imageRepository: ImageRepository by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
    }

    override fun setObservers() {
        viewModel.galleryImport.observe(this) {
            imageFromGallery()
        }

        viewModel.cameraImport.observe(this) {
            imageFromCamera()
        }
    }

    private fun imageFromCamera() {
        val i = Intent()
        i.action = MediaStore.ACTION_IMAGE_CAPTURE
        launchCameraActivity.launch(i)
    }

    private fun imageFromGallery() {
        val i = Intent()
        i.type = "image/*"
        i.action = Intent.ACTION_GET_CONTENT
        launchGalleryActivity.launch(i)
    }

    private var launchGalleryActivity = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        try {
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.let { intent ->
                    intent.data?.let {
                        if (Build.VERSION.SDK_INT < 28) {
                            val bitmap = MediaStore.Images.Media.getBitmap(
                                requireActivity().contentResolver,
                                it
                            )
                            imageRepository.saveImage(bitmap)
                        } else {
                            val source = ImageDecoder.createSource(
                                requireActivity().contentResolver,
                                it
                            )
                            imageRepository.saveImage(ImageDecoder.decodeBitmap(source))
                        }
                        navigateToCrop()
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private var launchCameraActivity = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            try {
                result.data?.let { intent ->
                    intent.extras?.let {
                        val imageBitmap = it.get("data") as Bitmap
                        imageRepository.saveImage(imageBitmap)
                        navigateToCrop()
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun navigateToCrop() {
        navigate(MainMenuFragmentDirections.actionMainMenuFragmentToCropFragment())
    }

}