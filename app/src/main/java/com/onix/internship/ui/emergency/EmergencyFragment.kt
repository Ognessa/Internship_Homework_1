package com.onix.internship.ui.emergency

import android.os.Bundle
import android.view.View
import com.onix.internship.R
import com.onix.internship.arch.BaseFragment
import com.onix.internship.databinding.EmergencyFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class EmergencyFragment : BaseFragment<EmergencyFragmentBinding>(R.layout.emergency_fragment){
    override val viewModel: EmergencyViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
    }

}