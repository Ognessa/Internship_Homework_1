package com.onix.internship.ui.soundList

import android.os.Bundle
import android.view.View
import com.onix.internship.R
import com.onix.internship.arch.BaseFragment
import com.onix.internship.arch.ext.navigate
import com.onix.internship.databinding.SoundListFragmentBinding
import com.onix.internship.objects.localObjects.RecordingData
import org.koin.androidx.viewmodel.ext.android.viewModel

class SoundListFragment : BaseFragment<SoundListFragmentBinding>(R.layout.sound_list_fragment) {

    override val viewModel: SoundListViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
    }

    override fun setObservers() {
        viewModel.navigate.observe(this){
            navigate(SoundListFragmentDirections.actionSoundListFragmentToMoreInfoFragment())
        }
    }

    private fun setupAdapter(){
        val soundClickListener = object : OnSoundClickListener{
            override fun playSound(item : RecordingData){
                viewModel.setNewRecording(item)
                viewModel.playPauseRecording()
            }
            override fun soundSelected(item : RecordingData){
                viewModel.selectRecord(item)
            }
        }

        val adapter = SoundListAdapter(soundClickListener)
        binding.recordsList.adapter = adapter
        adapter.submitList(viewModel.getRecordingsList())
    }

}