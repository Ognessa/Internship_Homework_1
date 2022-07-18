package com.onix.internship.ui.map

import com.onix.internship.arch.BaseFragment
import com.onix.internship.R
import com.onix.internship.databinding.MapFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MapFragment : BaseFragment<MapFragmentBinding>(R.layout.map_fragment){
    override val viewModel: MapViewModel by viewModel()
}