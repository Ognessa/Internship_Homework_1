package com.onix.internship.ui.addDevice

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.onix.internship.R
import com.onix.internship.arch.BaseFragment
import com.onix.internship.arch.ext.navigateToPrevious
import com.onix.internship.databinding.AddDeviceFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddDeviceFragment : BaseFragment<AddDeviceFragmentBinding>(R.layout.add_device_fragment){

    override val viewModel: AddDeviceViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel

        setupDeviceSpinner(binding.spinnerDevice)
        setupSubtypeSpinner(binding.spinnerSubtype)

    }

    override fun setObservers() {
        viewModel.returnToPrevious.observe(this){
            navigateToPrevious()
        }
    }

    fun setupDeviceSpinner(spinner : Spinner){
        val deviceList = resources.getStringArray(R.array.devices)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, deviceList)

        spinner.apply {
            this.adapter = adapter
            this.onItemSelectedListener = DeviceOnItemSelected(viewModel, deviceList)
        }
    }

    fun setupSubtypeSpinner(spinner : Spinner){
        val subtypeList = resources.getStringArray(R.array.subtype)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, subtypeList)

        spinner.apply {
            this.adapter = adapter
            this.onItemSelectedListener = SubtypeOnItemSelected(viewModel, subtypeList)
        }
    }

}