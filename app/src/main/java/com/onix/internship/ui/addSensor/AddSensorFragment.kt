package com.onix.internship.ui.addSensor

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import com.onix.internship.R
import com.onix.internship.arch.BaseFragment
import com.onix.internship.databinding.AddSensorFragmentBinding
import com.onix.internship.entity.SensorSubType
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddSensorFragment : BaseFragment<AddSensorFragmentBinding>(R.layout.add_sensor_fragment) {
    override val viewModel: AddSensorViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        setTypeSpinner()
        setSubTypeSpinner()
    }

    override fun setObservers() {
        super.setObservers()

        viewModel.moveBack.observe(viewLifecycleOwner) {
            findNavController().popBackStack()
        }
    }

    private fun setTypeSpinner() {
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.type,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.typeSpinnerChoice.adapter = adapter
            binding.typeSpinnerChoice.onItemSelectedListener = typeClickListener()

        }
    }

    private fun setSubTypeSpinner() {
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.sub_type,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.subTypeSpinnerChoice.adapter = adapter
            binding.subTypeSpinnerChoice.onItemSelectedListener = subTypeClickListener()
        }
    }

    private fun typeClickListener(): AdapterView.OnItemSelectedListener {
        return object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                viewModel.model.type.set(resources.getStringArray(R.array.type)[position])
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }
    }

    private fun subTypeClickListener(): AdapterView.OnItemSelectedListener {
        return object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                viewModel.model.subType.set(
                    when (resources.getStringArray(R.array.sub_type)[position]) {
                        "switch" -> {
                            SensorSubType.SWITCH
                        }
                        "onetime" -> {
                            SensorSubType.ONETIME
                        }
                        "level" -> {
                            SensorSubType.LEVEL
                        }
                        else -> {
                            SensorSubType.SWITCH
                        }
                    }
                )
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }
}