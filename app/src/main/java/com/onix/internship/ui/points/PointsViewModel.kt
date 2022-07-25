package com.onix.internship.ui.points

import com.onix.internship.arch.BaseViewModel
import com.onix.internship.objects.Point
import com.onix.internship.objects.PointsStore

class PointsViewModel(private val store : PointsStore) : BaseViewModel(){
    val pointList = store.pointsList

    fun updatePointsList(point: Point){
        store.pointsList.add(point)
    }

}