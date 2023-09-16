package com.onix.internship.ui.linear

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.onix.internship.R
import com.onix.internship.arch.controller.BaseViewModel
import com.onix.internship.arch.ui.fragment.BaseFragment
import com.onix.internship.databinding.FragmentLinearDadBinding
import com.onix.internship.ui.linear.adapter.LinearDADAdapter
import com.onix.internship.ui.linear.adapter.DragAndDropViewHolder
import com.onix.internship.ui.linear.adapter.model.LinearDADModel
import com.onix.internship.ui.linear.adapter.touch.ItemLinearMoveCallback
import org.koin.androidx.viewmodel.ext.android.viewModel

class LinearDADFragment : BaseFragment<FragmentLinearDadBinding>(R.layout.fragment_linear_dad) {

    private val viewModel: LinearDADViewModel by viewModel()

    private val listAdapter by lazy { LinearDADAdapter() }

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
            adapter = listAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        ItemTouchHelper(ItemLinearMoveCallback(
            adapter = object : ItemLinearMoveCallback.ItemTouchHelperContract {
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
                    listAdapter.notifyItemMoved(fromPosition, toPosition)
                }

                override fun onRowSelected(myViewHolder: DragAndDropViewHolder) {
                    myViewHolder.binding.root.setBackgroundColor(Color.GRAY)
                }

                override fun onRowClear(myViewHolder: DragAndDropViewHolder) {
                    myViewHolder.binding.root.setBackgroundColor(Color.TRANSPARENT)
                }

            }
        )).attachToRecyclerView(binding.dragAndDropList)
    }

    private fun setupList(list: List<LinearDADModel>) {
        listAdapter.items = list
    }

    override fun getViewModel(): BaseViewModel = viewModel
}