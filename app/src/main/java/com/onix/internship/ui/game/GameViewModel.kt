package com.onix.internship.ui.game

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.onix.internship.arch.BaseViewModel
import com.onix.internship.objects.GameString
import com.onix.internship.objects.Scenario

class GameViewModel(val scenario: Scenario) : BaseViewModel() {

    private val _currentText = MutableLiveData<String>()
    val currentText : LiveData<String> get() = _currentText

    val data = scenario.scenario
    var currentLabel = data.first().title
    var currentLine = 0

    init {
        getCurrentText()
    }

    fun nextLine(){
        currentLine++

        val lines = getCurrentLines()
        if(currentLine < lines.size)
            getCurrentText()
        else{
            currentLine = 0
            currentLabel= lines.last().line
            getCurrentText()
        }
    }

    fun getCurrentText() {
        val lines = getCurrentLines()
        _currentText.postValue(lines[currentLine].line)
    }

    fun getCurrentLines() : ArrayList<GameString>{
        val lines = arrayListOf<GameString>()
        data.forEach {
            if(it.title == currentLabel){
                lines.addAll(it.getGameStrings())
                Log.d("DEBUG", "Success, ${it.title}")
            }
        }
        return lines
    }

}