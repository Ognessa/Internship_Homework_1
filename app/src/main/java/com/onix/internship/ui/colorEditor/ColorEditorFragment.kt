package com.onix.internship.ui.colorEditor

import android.graphics.Color
import android.graphics.LightingColorFilter
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.core.graphics.drawable.toBitmap
import com.onix.internship.R
import com.onix.internship.arch.BaseFragment
import com.onix.internship.data.repository.ImageRepository
import com.onix.internship.databinding.ColorEditorFragmentBinding
import com.onix.internship.objects.ImageHelper
import com.onix.internship.ui.colorSeekbar.ColorSeekBar
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ColorEditorFragment : BaseFragment<ColorEditorFragmentBinding>(R.layout.color_editor_fragment) {

    override val viewModel: ColorEditorViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
        binding.ivEditImage.setImageBitmap(viewModel.getImage())
        setupColorSeekbar()
    }

    override fun setObservers() {
        viewModel.saveEvent.observe(this){
            viewModel.saveFilteredImage(binding.ivEditImage, requireActivity())
            viewModel.saveImageToMemory()
            showSnack("saved")
        }

        viewModel.restoreEvent.observe(this){
            binding.ivEditImage.colorFilter = null
        }
    }

    private fun setupColorSeekbar(){
        binding.colorFilter.apply {
            setOnColorChangeListener(object : ColorSeekBar.OnColorChangeListener {
                override fun onColorChangeListener(color: Int) {
                    changeHue(binding.ivEditImage, color)
                }
            })
        }
    }

    private fun changeHue(imageView: ImageView, @ColorInt color: Int) {
        val lightingColorFilter = LightingColorFilter(color, Color.BLACK)
        imageView.colorFilter = lightingColorFilter
    }
}