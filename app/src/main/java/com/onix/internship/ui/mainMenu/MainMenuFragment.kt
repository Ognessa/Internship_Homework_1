package com.onix.internship.ui.mainMenu

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.onix.internship.R
import com.onix.internship.arch.BaseFragment
import com.onix.internship.arch.ext.navigate
import com.onix.internship.databinding.MainMenuFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.IOException


class MainMenuFragment : BaseFragment<MainMenuFragmentBinding>(R.layout.main_menu_fragment) {

    override val viewModel: MainMenuViewModel by viewModel()

    val REQUEST_IMAGE_CAPTURE = 1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
    }

    override fun setObservers() {
        viewModel.galleryImport.observe(this) {
            checkGalleryPermission()
        }

        viewModel.cameraImport.observe(this) {
            checkCameraPermission()
        }
    }

    private fun checkGalleryPermission() {
        locationPermissionRequest.launch(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE))
    }

    private fun checkCameraPermission() {
        locationPermissionRequest.launch(arrayOf(Manifest.permission.CAMERA))
    }

    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.WRITE_EXTERNAL_STORAGE, false) -> {
                imageFromGallery()
            }
            permissions.getOrDefault(Manifest.permission.CAMERA, false) -> {
                imageFromCamera()
            }
            else -> {
                showSnack("Allow permission, please")
            }
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
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data

            if (data != null && data.data != null) {
                val selectedImageUri = data.data
                try {
                    val selectedImageBitmap = MediaStore.Images.Media.getBitmap(
                        requireActivity().contentResolver,
                        selectedImageUri
                    )
                    viewModel.saveImage(selectedImageBitmap)
                    navigate(MainMenuFragmentDirections.actionMainMenuFragmentToPhotoEditorFragment())
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    private var launchCameraActivity = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            try {
                val imageBitmap = data?.extras?.get("data") as Bitmap
                viewModel.saveImage(imageBitmap)
                navigate(MainMenuFragmentDirections.actionMainMenuFragmentToPhotoEditorFragment())
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

}