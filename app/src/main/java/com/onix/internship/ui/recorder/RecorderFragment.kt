package com.onix.internship.ui.recorder

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import com.onix.internship.R
import com.onix.internship.arch.controller.BaseViewModel
import com.onix.internship.arch.ui.fragment.BaseFragment
import com.onix.internship.databinding.FragmentRecorderBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class RecorderFragment : BaseFragment<FragmentRecorderBinding>(R.layout.fragment_recorder) {

    private val viewModel: RecorderViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.presenter = viewModel
        binding.model = viewModel.model
        binding.recordingBtn.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> viewModel.onStartRecording()
                MotionEvent.ACTION_UP -> viewModel.onStopRecording()
            }
            return@setOnTouchListener true
        }
    }

    override fun getViewModel(): BaseViewModel = viewModel

}