package com.onix.internship.ui.addDevice

import android.view.View
import android.widget.AdapterView

class SubtypeOnItemSelected (
    private val viewModel: AddDeviceViewModel,
    private val subtypeList: Array<out String>
) : AdapterView.OnItemSelectedListener {

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        viewModel.updateSubtype(subtypeList[position])
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {}
}