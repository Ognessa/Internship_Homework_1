package com.onix.internship.ui.memeList

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.onix.internship.R
import com.onix.internship.arch.BaseFragment
import com.onix.internship.arch.ext.onRefresh
import com.onix.internship.databinding.MemeListFragmentBinding
import com.onix.internship.ui.memeList.adapter.MemeAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MemeListFragment : BaseFragment<MemeListFragmentBinding>(R.layout.meme_list_fragment){

    override val viewModel: MemeListViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        val items = viewModel.items
        val adapter = MemeAdapter()
        binding.memeList.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch{
            repeatOnLifecycle(Lifecycle.State.STARTED){
                items.collectLatest {
                    adapter.submitData(viewModel.filterData(it))
                }
            }
        }

        binding.srl.onRefresh { adapter.refresh() }
        setupFilterSpinner(binding.memeFilter, adapter)
    }

    private fun setupFilterSpinner(spinner: Spinner, rvAdapter : MemeAdapter){
        val filters = resources.getStringArray(R.array.filter_list).toList()
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, filters)
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                viewModel.updateFilter(filters[pos])
                rvAdapter.refresh()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
    }
}