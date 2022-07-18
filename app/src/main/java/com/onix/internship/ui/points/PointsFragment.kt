package com.onix.internship.ui.points

import com.onix.internship.arch.BaseFragment
import com.onix.internship.R
import com.onix.internship.databinding.PointsFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class PointsFragment : BaseFragment<PointsFragmentBinding>(R.layout.points_fragment){
    override val viewModel: PointsViewModel by viewModel()
}