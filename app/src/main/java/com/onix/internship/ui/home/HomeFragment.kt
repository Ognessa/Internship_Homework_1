package com.onix.internship.ui.home

import android.os.Bundle
import android.view.View
import com.onix.internship.R
import com.onix.internship.arch.controller.BaseViewModel
import com.onix.internship.arch.ui.fragment.BaseFragment
import com.onix.internship.databinding.FragmentHomeBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val viewModel by viewModel<HomeViewModel>()

    override fun getViewModel(): BaseViewModel = viewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.model = viewModel.model
        binding.nameInputLayout.setEndIconOnClickListener {
            viewModel.searchPersonData()
        }
    }

}