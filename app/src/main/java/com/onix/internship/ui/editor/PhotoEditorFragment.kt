package com.onix.internship.ui.editor

import android.os.Bundle
import android.view.View
import com.google.android.material.slider.Slider
import com.onix.internship.R
import com.onix.internship.arch.BaseFragment
import com.onix.internship.arch.ext.navigate
import com.onix.internship.databinding.PhotoEditorFragmentBinding
import com.onix.internship.objects.FilterData
import org.koin.androidx.viewmodel.ext.android.viewModel


class PhotoEditorFragment : BaseFragment<PhotoEditorFragmentBinding>(R.layout.photo_editor_fragment) {

    override val viewModel: PhotoEditorViewModel by viewModel()
    private lateinit var filterData : FilterData

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        filterData = viewModel.getFilterData()
        binding.ivEditImage.setImageBitmap(viewModel.getImage())

        setupContrastSlider()
        setupBrightnessSlider()
        setupSaturationSlider()
    }

    override fun setObservers() {
        viewModel.model.saveAndNavigateEvent.observe(this){
            viewModel.saveFilteredImage(binding.ivEditImage, requireActivity())
            navigate(PhotoEditorFragmentDirections.actionPhotoEditorFragmentToColorEditorFragment())
        }

        viewModel.model.restoreEvent.observe(this){
            filterData = viewModel.restoreFilter()
            binding.contrastSlider.value = filterData.contrast
            binding.brightnessSlider.value = filterData.brightness
            binding.saturationSlider.value = filterData.saturation
        }
    }

    private fun setupContrastSlider() {
        binding.contrastSlider.apply {
            addOnChangeListener { _, value, _ ->
                binding.ivEditImage.contrast = value
                filterData.contrast = value
            }

            addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
                override fun onStartTrackingTouch(slider: Slider) {}
                override fun onStopTrackingTouch(slider: Slider) {
                    viewModel.saveFilterData(filterData.copy())
                }
            })
        }
    }

    private fun setupBrightnessSlider(){
        binding.brightnessSlider.apply {
            addOnChangeListener { _, value, _ ->
                binding.ivEditImage.brightness = value
                filterData.brightness = value
            }

            addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
                override fun onStartTrackingTouch(slider: Slider) {}
                override fun onStopTrackingTouch(slider: Slider) {
                    viewModel.saveFilterData(filterData.copy())
                }
            })
        }
    }

    private fun setupSaturationSlider(){
        binding.saturationSlider.apply {
            addOnChangeListener { _, value, _ ->
                binding.ivEditImage.saturation = value
                filterData.saturation = value
            }

            addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
                override fun onStartTrackingTouch(slider: Slider) {}
                override fun onStopTrackingTouch(slider: Slider) {
                    viewModel.saveFilterData(filterData.copy())
                }
            })
        }
    }

}