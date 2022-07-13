package com.onix.internship.ui.taskList

import android.icu.text.SimpleDateFormat
import com.onix.internship.arch.BaseViewModel
import com.onix.internship.objects.WeekDayItem
import java.util.*
import kotlin.collections.ArrayList

class TaskListViewModel : BaseViewModel(){

    private val WEEK_DAYS_COUNT = 7
    private val weekDay : SimpleDateFormat = SimpleDateFormat("EEE")
    private val weekDayNum : SimpleDateFormat = SimpleDateFormat("dd")
    private val weekMounth : SimpleDateFormat = SimpleDateFormat("MMMM")

    var currentDay :  String = ""
    var currentMounth :  String = ""

    init {
        val c = Calendar.getInstance()
        currentDay = weekDay.format(c.time)
        currentMounth = weekMounth.format(c.time)
    }

    fun getWeekDaysList(): ArrayList<WeekDayItem> {
        val list = arrayListOf<WeekDayItem>()

        val c = Calendar.getInstance()
        c[Calendar.DAY_OF_WEEK] = Calendar.MONDAY

        repeat(WEEK_DAYS_COUNT) {
            list.add(WeekDayItem(weekDay.format(c.time), weekDayNum.format(c.time).toInt()))
            c.add(Calendar.DATE, 1)
        }

        return list
    }
}