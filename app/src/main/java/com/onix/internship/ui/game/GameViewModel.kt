package com.onix.internship.ui.game

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.onix.internship.arch.BaseViewModel
import com.onix.internship.objects.Actions
import com.onix.internship.objects.GameMenuButton
import com.onix.internship.objects.GameString
import com.onix.internship.objects.Scenario

class GameViewModel(val scenario: Scenario) : BaseViewModel() {

    private val _currentText = MutableLiveData<String>()
    val currentText : LiveData<String> get() = _currentText

    private val _hasMenu = MutableLiveData<Boolean>()
    val hasMenu : LiveData<Boolean> get() = _hasMenu

    val data = scenario.scenario
    var currentLabel = data.first().title
    var currentLine = 0

    init {
        getCurrentText()
    }

    fun nextLine(){
        currentLine++
        val lines = getCurrentLines()

        if(currentLine < lines.size){
            when(lines[currentLine].action){
                Actions.CHANGE_TEXT -> {
                    getCurrentText()
                }
                Actions.MENU_TITLE -> {
                    _hasMenu.postValue(true)
                    getCurrentText()
                }
                Actions.JUMP -> {
                    jump(lines.last().line)
                }
                else -> {
                    nextLine()
                }
            }
        }
        else{
            jump(lines.last().line)
        }
    }

    fun getCurrentText() {
        val lines = getCurrentLines()
        _currentText.postValue(lines[currentLine].line)
    }

    fun jump(label : String){
        currentLine = 0
        currentLabel= label
        getCurrentText()
        _hasMenu.postValue(false)
    }

    fun getCurrentLines() : ArrayList<GameString>{
        val lines = arrayListOf<GameString>()
        data.forEach {
            if(it.title == currentLabel){
                lines.addAll(it.getGameStrings())
            }
        }
        return lines
    }

    fun getMenuButtons(): ArrayList<GameMenuButton> {
        val buttonsList = arrayListOf<GameMenuButton>()
        data.forEach {
            if(it.title == currentLabel){
                buttonsList.addAll(it.getGameMenu())
                Log.d("DEBUG", "Success, ${it.title}")
            }
        }
        return buttonsList
    }

}