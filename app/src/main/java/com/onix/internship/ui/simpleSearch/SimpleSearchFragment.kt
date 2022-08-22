package com.onix.internship.ui.simpleSearch

import android.os.Bundle
import android.view.View
import com.onix.internship.R
import com.onix.internship.arch.BaseFragment
import com.onix.internship.databinding.SimpleSearchFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SimpleSearchFragment : BaseFragment<SimpleSearchFragmentBinding>(R.layout.simple_search_fragment){

    override val viewModel: SimpleSearchViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
    }

}