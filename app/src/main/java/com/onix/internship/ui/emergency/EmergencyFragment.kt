package com.onix.internship.ui.emergency

import com.onix.internship.R
import com.onix.internship.arch.BaseFragment
import com.onix.internship.databinding.EmergencyFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class EmergencyFragment : BaseFragment<EmergencyFragmentBinding>(R.layout.emergency_fragment){
    override val viewModel: EmergencyViewModel by viewModel()
}