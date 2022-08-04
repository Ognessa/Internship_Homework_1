package com.onix.internship.ui.sensors

import android.os.Bundle
import android.view.View
import com.onix.internship.R
import com.onix.internship.arch.BaseFragment
import com.onix.internship.arch.ext.navigate
import com.onix.internship.arch.ext.onRefresh
import com.onix.internship.databinding.SensorsListFragmentBinding
import com.onix.internship.ui.sensors.adapter.SensorsAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class SensorsListFragment :
    BaseFragment<SensorsListFragmentBinding>(R.layout.sensors_list_fragment) {
    override val viewModel: SensorsListViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        binding.swipeRefreshLayout.onRefresh { viewModel.getDataFromApi() }

        viewModel.getDataFromApi()
        viewModel.updateListItem()
        setAdapter()
    }

    override fun setObservers() {
        super.setObservers()

        viewModel.moveToAddFragment.observe(viewLifecycleOwner) {
            navigateToAddFragment()
        }
    }

    private fun setAdapter() {
        val notesAdapter = SensorsAdapter(viewModel)

        binding.notesRecycler.adapter = notesAdapter

        viewModel.listOfSensors.observe(viewLifecycleOwner) {
            notesAdapter.submitList(it)
        }
    }

    private fun navigateToAddFragment() {
        navigate(SensorsListFragmentDirections.actionSensorsListFragmentToAddSensorFragment())
    }
}