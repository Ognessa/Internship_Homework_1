package com.onix.internship.ui.tictactoe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.onix.internship.R
import com.onix.internship.arch.BaseFragment
import com.onix.internship.databinding.TicTacToeFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class TicTacToeFragment : BaseFragment<TicTacToeFragmentBinding>(R.layout.tic_tac_toe_fragment) {

    override val viewModel: TicTacToeViewModel by viewModel()
    private val args : TicTacToeFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        val size = requireContext().resources.getDimension(R.dimen.tic_tac_toe_size)
        viewModel.initStartValues(args.userSymbol, size, size)

        return view
    }

}