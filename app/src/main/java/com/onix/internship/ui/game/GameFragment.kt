package com.onix.internship.ui.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.onix.internship.R
import com.onix.internship.arch.BaseFragment
import com.onix.internship.databinding.GameFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class GameFragment : BaseFragment<GameFragmentBinding>(R.layout.game_fragment) {

    override val viewModel: GameViewModel by viewModel()
    var pause_flag = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        binding.fabPauseMenu.setOnClickListener { showPauseMenu(binding) }

        return view
    }

    fun showPauseMenu(binding: GameFragmentBinding){
        val action = GameFragmentDirections.actionGameFragmentToPauseFragment()
        findNavController().navigate(action)
    }

}