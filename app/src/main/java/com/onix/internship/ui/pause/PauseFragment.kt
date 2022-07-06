package com.onix.internship.ui.pause

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.onix.internship.R
import com.onix.internship.arch.BaseFragment
import com.onix.internship.databinding.FragmentPauseBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class PauseFragment : BaseFragment<FragmentPauseBinding>(R.layout.fragment_pause) {

    override val viewModel: PauseViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        binding.btnContinue.setOnClickListener { continueGame() }
        binding.btnContinue.setOnClickListener { continueGame() }
        binding.btnQuit.setOnClickListener { activity?.finish() }

        return view
    }

    fun continueGame(){
        val action = PauseFragmentDirections.actionPauseFragmentToGameFragment()
        findNavController().navigate(action)
    }
}