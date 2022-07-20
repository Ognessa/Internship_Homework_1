package com.onix.internship.ui.home

import com.onix.internship.R
import com.onix.internship.arch.BaseFragment
import com.onix.internship.databinding.HomeFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<HomeFragmentBinding>(R.layout.home_fragment) {

    override val viewModel : HomeViewModel by viewModel()

}