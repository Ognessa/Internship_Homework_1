package com.onix.internship.ui.crop

import android.os.Bundle
import android.view.View
import androidx.core.graphics.drawable.toBitmap
import com.onix.internship.R
import com.onix.internship.arch.BaseFragment
import com.onix.internship.arch.ext.navigate
import com.onix.internship.data.repository.ImageRepository
import com.onix.internship.databinding.CropFragmentBinding
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class CropFragment : BaseFragment<CropFragmentBinding>(R.layout.crop_fragment){

    override val viewModel: CropViewModel by viewModel()
    private val imageRepository : ImageRepository by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        binding.imageCropView.apply {
            setImageBitmap(imageRepository.getImage())
            setAspectRatio(1, 1)
        }

    }

    override fun setObservers() {
        val cropView = binding.imageCropView

        viewModel.ratioEvent.observe(this) {
            val widthRatio = it.first
            val heightRatio = it.second
            if (isPossibleCrop(widthRatio, heightRatio)) {
                cropView.setAspectRatio(widthRatio, heightRatio)
            } else {
                showSnack(resources.getString(R.string.can_not_crop))
            }
        }

        viewModel.cropEvent.observe(this) {
            val croppedImage = cropView.croppedImage
            cropView.setImageBitmap(croppedImage)
            imageRepository.saveImage(croppedImage)
            binding.restore.isEnabled = true
        }

        viewModel.saveEvent.observe(this) {
            imageRepository.saveImage(cropView.drawable.toBitmap())
            imageRepository.saveAllChanges()
            navigate(CropFragmentDirections.actionCropFragmentToPhotoEditorFragment())
        }

        viewModel.restoreEvent.observe(this)  {
            cropView.setImageBitmap(imageRepository.restoreImage())
            binding.restore.isEnabled = false
        }
    }

    private fun isPossibleCrop(widthRatio: Int, heightRatio: Int): Boolean {
        val bitmap = binding.imageCropView.viewBitmap
        bitmap?.let {
            val width = it.width
            val height = it.height
            return !(width < widthRatio && height < heightRatio)
        }
        return false
    }

}