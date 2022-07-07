package com.onix.internship.ui.pause

import com.onix.internship.arch.BaseViewModel
import com.onix.internship.objects.Scenario

class PauseViewModel(private val scenario: Scenario) : BaseViewModel() {

    fun stepBack(){
        scenario.currentLine--
    }

    fun clearData(){
        scenario.currentLine = -1
        scenario.currentLabel = scenario.scenario.first().title
    }
}