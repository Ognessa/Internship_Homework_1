package com.onix.internship.ui.addDevice

import android.view.View
import android.widget.AdapterView

class DeviceOnItemSelected(
    private val viewModel: AddDeviceViewModel,
    private val deviceList: Array<out String>
) : AdapterView.OnItemSelectedListener {

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        viewModel.updateDevice(deviceList[position])
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {}
}