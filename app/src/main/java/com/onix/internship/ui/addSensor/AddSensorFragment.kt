package com.onix.internship.ui.addSensor

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.onix.internship.R
import com.onix.internship.arch.BaseFragment
import com.onix.internship.arch.ext.subtypesList
import com.onix.internship.arch.ext.typesList
import com.onix.internship.databinding.AddSensorFragmentBinding
import com.onix.internship.entity.SensorSubType
import com.onix.internship.entity.SensorType
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddSensorFragment : BaseFragment<AddSensorFragmentBinding>(R.layout.add_sensor_fragment) {
    override val viewModel: AddSensorViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        binding.typeSpinnerChoice.typesList(SensorType.values()){
                type -> viewModel.updateType(type)
        }

        binding.subTypeSpinnerChoice.subtypesList(SensorSubType.values()){
            subtype -> viewModel.updateSubtype(subtype)
        }
    }

    override fun setObservers() {
        viewModel.moveBack.observe(viewLifecycleOwner) {
            findNavController().popBackStack()
        }
    }
}