package com.onix.internship.ui.moreInfo

import android.os.Bundle
import android.view.View
import com.onix.internship.R
import com.onix.internship.arch.BaseFragment
import com.onix.internship.databinding.MoreInfoFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MoreInfoFragment : BaseFragment<MoreInfoFragmentBinding>(R.layout.more_info_fragment) {

    override val viewModel: MoreInfoViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
    }
}