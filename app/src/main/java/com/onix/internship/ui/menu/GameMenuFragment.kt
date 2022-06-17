package com.onix.internship.ui.menu

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.onix.internship.R
import com.onix.internship.arch.BaseFragment
import com.onix.internship.databinding.FragmentGameMenuBinding
import com.onix.internship.ui.tictactoe.Symbol

class GameMenuFragment : BaseFragment<FragmentGameMenuBinding>(R.layout.fragment_game_menu) {

    override val viewModel: GameMenuViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ibtnCross.setOnClickListener{ startGame(Symbol.CROSS) }
        binding.ibtnCircle.setOnClickListener{ startGame(Symbol.CIRCLE) }
    }

    fun startGame(symbol: Symbol){
        val action = GameMenuFragmentDirections.actionGameMenuFragmentToTicTacToeFragment(symbol)
        findNavController().navigate(action)
    }
}