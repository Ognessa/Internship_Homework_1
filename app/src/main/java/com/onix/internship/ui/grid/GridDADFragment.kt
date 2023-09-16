package com.onix.internship.ui.grid

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import com.onix.internship.R
import com.onix.internship.arch.controller.BaseViewModel
import com.onix.internship.arch.ui.fragment.BaseFragment
import com.onix.internship.databinding.FragmentGridDadBinding
import com.onix.internship.ui.grid.adapter.GridDADViewHolder
import com.onix.internship.ui.grid.adapter.GridDadAdapter
import com.onix.internship.ui.grid.adapter.model.GridDadModel
import com.onix.internship.ui.grid.touch.ItemGridMoveCallback
import org.koin.androidx.viewmodel.ext.android.viewModel

class GridDADFragment : BaseFragment<FragmentGridDadBinding>(R.layout.fragment_grid_dad) {

    private val viewModel: GridDadViewModel by viewModel()

    private val gradAdapter by lazy { GridDadAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.presenter = viewModel
        setupAdapter()
    }

    override fun setObservers() {
        super.setObservers()
        viewModel.ddList.observe(viewLifecycleOwner, ::setupList)
    }

    private fun setupAdapter() {
        binding.dragAndDropList.apply {
            adapter = gradAdapter
            layoutManager = GridLayoutManager(requireContext(), 3)
        }
        ItemTouchHelper(ItemGridMoveCallback(
            adapter = object : ItemGridMoveCallback.ItemTouchHelperContract {
                override fun onRowMoved(fromPosition: Int, toPosition: Int) {
                    if (fromPosition < toPosition) {
                        for (i in fromPosition..toPosition) {
                            viewModel.swapItem(i, i + 1)
                        }
                    } else {
                        for (i in toPosition..fromPosition) {
                            viewModel.swapItem(i, i - 1)
                        }
                    }
                    gradAdapter.notifyItemMoved(fromPosition, toPosition)
                }

                override fun onRowSelected(myViewHolder: GridDADViewHolder) {
                    myViewHolder.binding.root.setBackgroundColor(Color.GRAY)
                }

                override fun onRowClear(myViewHolder: GridDADViewHolder) {
                    myViewHolder.binding.root.setBackgroundColor(Color.TRANSPARENT)
                }

            }
        )).attachToRecyclerView(binding.dragAndDropList)
    }

    private fun setupList(list: List<GridDadModel>) {
        gradAdapter.items = list
    }

    override fun getViewModel(): BaseViewModel = viewModel

}