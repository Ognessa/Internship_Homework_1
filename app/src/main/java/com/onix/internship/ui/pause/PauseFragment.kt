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
        binding.btnRestart.setOnClickListener { restartGame() }
        binding.btnQuit.setOnClickListener { requireActivity().finish() }
        binding.ivMusic.setOnClickListener { viewModel.playOrStopMusic() }

        return view
    }

    override fun setObservers() {
        //change music sound image
        viewModel.musicStatus.observe(this){
            val drawableId = if(it) R.drawable.ic_music_on else R.drawable.ic_music_off
            binding.ivMusic.setImageDrawable(requireContext().getDrawable(drawableId))
        }
    }

    private fun continueGame(){
        viewModel.stepBack()
        findNavController().navigate(PauseFragmentDirections.actionPauseFragmentToGameFragment())
    }

    private fun restartGame(){
        viewModel.clearData()
        findNavController().navigate(PauseFragmentDirections.actionPauseFragmentToGameFragment())
    }
}