package com.onix.internship.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.SeekBar
import com.onix.internship.arch.BaseFragment
import com.onix.internship.R
import com.onix.internship.databinding.SettingsFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment : BaseFragment<SettingsFragmentBinding>(R.layout.settings_fragment){
    override val viewModel: SettingsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        createClassesList(binding.rgClasses)
        checkLevelChanges(binding.sbLevel)

        binding.rgClasses.setOnCheckedChangeListener { _, i ->
            viewModel.updateClass(i)
        }

        return view
    }

    private fun checkLevelChanges(sbLevel : SeekBar){
        sbLevel.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                viewModel.updateLevel(p1)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {}
            override fun onStopTrackingTouch(p0: SeekBar?) {}
        })
    }

    private fun createClassesList(rgClasses : RadioGroup){
        rgClasses.removeAllViews()

        val list = resources.getStringArray(R.array.class_titles)
        list.forEach {
            val rb = RadioButton(requireContext())
            rb.id = list.indexOf(it)
            rb.text = it
            rgClasses.addView(rb)
            if(rb.id == viewModel.pointClass.value)
                rb.isChecked = true
        }
    }

}