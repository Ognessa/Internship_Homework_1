package com.onix.internship.ui.tictactoe

import com.onix.internship.R
import com.onix.internship.arch.BaseFragment
import com.onix.internship.databinding.TicTacToeFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class TicTacToeFragment : BaseFragment<TicTacToeFragmentBinding>(R.layout.tic_tac_toe_fragment) {

    override val viewModel: TicTacToeViewModel by viewModel()

}