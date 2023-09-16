package com.onix.internship.ui.grid

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.onix.internship.arch.controller.BaseViewModel
import com.onix.internship.arch.router.command.NavDirection
import com.onix.internship.ui.grid.adapter.model.GridDadModel
import java.util.Collections

class GridDadViewModel : BaseViewModel(), GridDadPresenter {
    private val _ddList = MutableLiveData<List<GridDadModel>>()
    val ddList: LiveData<List<GridDadModel>> = _ddList

    private var data = listOf<GridDadModel>()

    init {
        init()
    }

    private fun init() {
        val list = mutableListOf<GridDadModel>()
        for (i in 1..100) {
            list.add(GridDadModel(i))
        }
        data = list
        _ddList.value = data
    }

    override fun swapItem(fromPosition: Int, toPosition: Int) {
        Collections.swap(data, fromPosition, toPosition)
        _ddList.value = data
    }

    override fun onMoveToLinearPressed() {
        navigate(
            NavDirection.Direction(
                GridDADFragmentDirections.actionGridDADFragmentToLinearDADFragment()
            )
        )
    }
}