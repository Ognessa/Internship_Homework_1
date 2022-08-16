package com.onix.internship.ui.crop

import android.os.Bundle
import android.view.View
import com.onix.internship.R
import com.onix.internship.arch.BaseFragment
import com.onix.internship.arch.ext.navigate
import com.onix.internship.databinding.CropFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class CropFragment : BaseFragment<CropFragmentBinding>(R.layout.crop_fragment){

    override val viewModel: CropViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        binding.imageCropView.setImageBitmap(viewModel.getImage())
    }

    override fun setObservers() {
        val cropView = binding.imageCropView

        viewModel.model.cropEvent.observe(this) {
            val croppedImage = cropView.croppedImage
            cropView.setImageBitmap(croppedImage)
            viewModel.saveImage(croppedImage)
        }

        viewModel.model.saveAndNavigateEvent.observe(this) {
            viewModel.saveResult(cropView.viewBitmap)
            navigateToPhotoEditor()
        }

        viewModel.model.restoreEvent.observe(this)  {
            cropView.setImageBitmap(viewModel.restoreImage())
        }
    }

    private fun navigateToPhotoEditor(){
        navigate(CropFragmentDirections.actionCropFragmentToPhotoEditorFragment())
    }

}