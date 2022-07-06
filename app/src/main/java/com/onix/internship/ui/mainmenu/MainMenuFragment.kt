package com.onix.internship.ui.mainmenu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.onix.internship.R
import com.onix.internship.arch.BaseFragment
import com.onix.internship.databinding.MainMenuFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainMenuFragment : BaseFragment<MainMenuFragmentBinding>(R.layout.main_menu_fragment) {

    override val viewModel: MainMenuViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        binding.btnStart.setOnClickListener { startGame() }
        binding.btnQuit.setOnClickListener { activity?.finish() }

        return view
    }

    fun startGame(){
        val action = MainMenuFragmentDirections.actionMainMenuFragmentToGameFragment()
        findNavController().navigate(action)
    }

}