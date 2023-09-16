package com.onix.internship.ui.linear

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.onix.internship.arch.controller.BaseViewModel
import com.onix.internship.arch.router.command.NavDirection
import com.onix.internship.ui.linear.adapter.model.LinearDADModel
import java.util.Collections

class LinearDADViewModel : BaseViewModel(), LinearDADPresenter {

    private val _ddList = MutableLiveData<List<LinearDADModel>>()
    val ddList: LiveData<List<LinearDADModel>> = _ddList

    private var data = listOf<LinearDADModel>()

    init {
        init()
    }

    private fun init() {
        val list = mutableListOf<LinearDADModel>()
        for (i in 1..100) {
            list.add(LinearDADModel(i))
        }
        data = list
        _ddList.value = data
    }

    override fun swapItem(fromPosition: Int, toPosition: Int) {
        Collections.swap(data, fromPosition, toPosition)
        _ddList.value = data
    }

    override fun onMoveToGridPressed() {
        navigate(
            NavDirection.Direction(
                LinearDADFragmentDirections.actionLinearDADFragmentToGridDADFragment()
            )
        )
    }

}