package com.onix.internship.ui.help

import android.os.Bundle
import android.view.View
import com.onix.internship.R
import com.onix.internship.arch.BaseFragment
import com.onix.internship.arch.ext.navigateToPrevious
import com.onix.internship.databinding.HelpFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class HelpFragment : BaseFragment<HelpFragmentBinding>(R.layout.help_fragment){
    override val viewModel: HelpViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setNavigationOnClickListener {
            navigateToPrevious()
        }
    }

}