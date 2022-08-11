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
    private val imageRepository: ImageRepository by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
        binding.ivEditImage.setImageBitmap(imageRepository.getImage())
        setupColorSeekbar()
    }

    override fun setObservers() {
        viewModel.saveEvent.observe(this){
            imageRepository.saveImage(
                ImageHelper().createFilteredImage(binding.ivEditImage)
            )
            imageRepository.saveAllChanges()
            showSnack("saved")
        }

        viewModel.restoreEvent.observe(this){
        }
    }

    private fun setupColorSeekbar(){
        binding.colorFilter.apply {
            setOnColorChangeListener(object : ColorSeekBar.OnColorChangeListener {
                override fun onColorChangeListener(color: Int) {
                    changeHue(binding.ivEditImage, color)
                }
            })

            setOnStopTouchTrack(object : ColorSeekBar.OnStopTouchTrack{
                override fun onStopTouchTrack() {
                    imageRepository.saveImage(binding.ivEditImage.drawable.toBitmap())
                }
            })
        }
    }

    private fun changeHue(imageView: ImageView, @ColorInt color: Int) {
        val lightingColorFilter = LightingColorFilter(color, Color.BLACK)
        imageView.colorFilter = lightingColorFilter
    }
}