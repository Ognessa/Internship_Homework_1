package com.onix.internship.ui.deviceList

import android.os.Bundle
import android.view.View
import com.onix.internship.R
import com.onix.internship.arch.BaseFragment
import com.onix.internship.arch.ext.navigate
import com.onix.internship.databinding.DeviceListFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class DeviceListFragment : BaseFragment<DeviceListFragmentBinding>(R.layout.device_list_fragment) {

    override val viewModel: DeviceListViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        viewModel.getDataFromApi()

        val adapter = DevicesAdapter()
        adapter.setContent(viewModel.getDeviceList())
        binding.rvDeviceList.adapter = adapter

    }

    override fun setObservers() {
        viewModel.navigateToAddDevices.observe(this){
            navigate(DeviceListFragmentDirections.actionDeviceListFragmentToAddDeviceFragment())
        }
    }

}