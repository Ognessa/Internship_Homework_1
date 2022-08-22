package com.onix.internship.ui.advancedSearch

import android.os.Bundle
import android.view.View
import com.onix.internship.R
import com.onix.internship.arch.BaseFragment
import com.onix.internship.arch.ext.setListData
import com.onix.internship.databinding.AdvancedSearchFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class AdvancedSearchFragment : BaseFragment<AdvancedSearchFragmentBinding>(R.layout.advanced_search_fragment) {

    override val viewModel: AdvancedSearchViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        binding.country.setListData(resources.getStringArray(R.array.countries_array)){
            viewModel.model.cnt.set(it)
        }
    }

}