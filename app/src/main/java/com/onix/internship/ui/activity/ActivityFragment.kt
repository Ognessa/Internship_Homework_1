package com.onix.internship.ui.activity

import com.onix.internship.R
import com.onix.internship.arch.BaseFragment
import com.onix.internship.databinding.ActivityFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ActivityFragment : BaseFragment<ActivityFragmentBinding>(R.layout.activity_fragment) {

    override val viewModel: ActivityViewModel by viewModel()

}