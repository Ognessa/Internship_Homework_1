package com.onix.internship.ui.age

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.onix.internship.arch.BaseFragment
import com.onix.internship.R
import com.onix.internship.databinding.AskAgeFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class AskAgeFragment : BaseFragment<AskAgeFragmentBinding>(R.layout.ask_age_fragment) {

    override val viewModel: AskAgeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        binding.ageViewModel = viewModel
        binding.btnNext.setOnClickListener { moveNext() }

        return view
    }

    private fun moveNext(){
        findNavController().navigate(AskAgeFragmentDirections.actionAskAgeFragmentToAskLevelFragment())
    }

}