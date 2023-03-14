package com.onix.internship.ui.splash

import android.os.Bundle
import android.view.View
import com.onix.internship.R
import com.onix.internship.arch.controller.BaseViewModel
import com.onix.internship.arch.router.command.NavDirection
import com.onix.internship.arch.ui.fragment.BaseFragment
import com.onix.internship.databinding.SplashFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashFragment : BaseFragment<SplashFragmentBinding>(R.layout.splash_fragment) {

    private val viewModel: SplashViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button.setOnClickListener {
            navigate(NavDirection.Direction(SplashFragmentDirections.actionSplashFragmentToCustomCameraActivity()))
        }
    }

    override fun getViewModel(): BaseViewModel = viewModel
}