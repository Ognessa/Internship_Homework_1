package com.onix.internship.ui.level

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.navigation.fragment.findNavController
import com.onix.internship.arch.BaseFragment
import com.onix.internship.R
import com.onix.internship.databinding.AskLevelFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class AskLevelFragment : BaseFragment<AskLevelFragmentBinding>(R.layout.ask_level_fragment){
    override val viewModel: AskLevelViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        binding.levelViewModel = viewModel

        viewModel.updateLevel(binding.sbLevel.progress)
        checkLevelChanges(binding.sbLevel)

        binding.btnPrev.setOnClickListener { movePrev() }
        binding.btnNext.setOnClickListener { moveNext() }

        return view
    }

    private fun checkLevelChanges(sbLevel : SeekBar){
        sbLevel.setOnSeekBarChangeListener(object : OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                viewModel.updateLevel(p1)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {}
            override fun onStopTrackingTouch(p0: SeekBar?) {}
        })
    }

    private fun moveNext(){
        findNavController().navigate(AskLevelFragmentDirections.actionAskLevelFragmentToAskClassFragment())
    }

    private fun movePrev(){
        findNavController().navigate(AskLevelFragmentDirections.actionAskLevelFragmentToAskAgeFragment())
    }

}

