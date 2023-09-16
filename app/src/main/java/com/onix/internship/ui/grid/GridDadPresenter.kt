package com.onix.internship.ui.grid

interface GridDadPresenter {

    fun swapItem(fromPosition: Int, toPosition: Int)

    fun onMoveToLinearPressed()
}