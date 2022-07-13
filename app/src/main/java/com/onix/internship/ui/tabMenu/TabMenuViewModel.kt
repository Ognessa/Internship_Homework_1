package com.onix.internship.ui.tabMenu

import android.icu.text.SimpleDateFormat
import com.onix.internship.arch.BaseViewModel
import java.util.*

class TabMenuViewModel : BaseViewModel(){
    private val selectedDateFormat : SimpleDateFormat = SimpleDateFormat("dd MMMM YYYY")
    private var selectedCalendarDate = ""

    init {
        val c = Calendar.getInstance()
        selectedCalendarDate = selectedDateFormat.format(c.time)
    }

    fun setSelectedCalendarDate(year : Int, mouth : Int, day : Int){
        val mouths = arrayListOf("January", "February", "March", "April", "May",
            "June", "July", "August", "September", "October", "November", "December")
        selectedCalendarDate = "$day ${mouths[mouth]} $year"
    }

    fun getSelectedCalendarDate(): String {
        return selectedCalendarDate
    }
}