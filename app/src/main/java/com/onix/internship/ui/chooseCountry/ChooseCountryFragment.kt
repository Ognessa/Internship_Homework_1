package com.onix.internship.ui.chooseCountry

import com.onix.internship.R
import com.onix.internship.arch.controller.BaseViewModel
import com.onix.internship.arch.ui.fragment.BaseFragment
import com.onix.internship.databinding.FragmentChooseCountryBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChooseCountryFragment :
    BaseFragment<FragmentChooseCountryBinding>(R.layout.fragment_choose_country) {

    private val viewModel: ChooseCountryViewModel by viewModel()

    override fun getViewModel(): BaseViewModel = viewModel
}