package com.onix.internship.ui.game

import android.graphics.Color
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.onix.internship.arch.BaseViewModel
import com.onix.internship.arch.lifecycle.SingleLiveEvent
import com.onix.internship.objects.Actions
import com.onix.internship.objects.Character
import com.onix.internship.objects.GameMenuButton
import com.onix.internship.objects.GameString
import com.onix.internship.objects.Scenario

class GameViewModel(val scenario: Scenario) : BaseViewModel() {

    //Character name and color
    private val _currentCharacter = MutableLiveData<Character>()
    val currentCharacter : LiveData<Character> get() = _currentCharacter

    //Just text
    private val _currentText = MutableLiveData<String>()
    val currentText : LiveData<String> get() = _currentText

    //scene background
    private val _currentScene = MutableLiveData<String>()
    val currentScene : LiveData<String> get() = _currentScene

    //character image
    private val _currentCharacterImage = MutableLiveData<String>()
    val currentCharacterImage : LiveData<String> get() = _currentCharacterImage

    //return to main menu
    val returnEvent = SingleLiveEvent<Boolean>()

    //show dialog menu
    private val _hasMenu = MutableLiveData<Boolean>()
    val hasMenu : LiveData<Boolean> get() = _hasMenu



    val data = scenario.scenario
    var currentLabel = data.first().title
    var currentLine = -1

    init {
        nextLine()
    }

    fun nextLine(){
        currentLine++
        val lines = getCurrentLines()

        if(currentLine < lines.size){
            when(lines[currentLine].action){
                Actions.CHANGE_TEXT -> {
                    var line = lines[currentLine].line
                    val character = findCharacter(line.first().toString())
                    _currentCharacter.postValue(character)
                    line = line.subSequence(line.indexOf("\"") + 1, line.lastIndexOf("\"")).toString()
                    _currentText.postValue(line)
                }
                Actions.SCENE -> {
                    val line = lines[currentLine].line
                    _currentScene.postValue(line)
                    _currentCharacterImage.postValue(null)
                    nextLine()
                }
                Actions.SHOW -> {
                    val line = lines[currentLine].line
                    _currentCharacterImage.postValue(line)
                    nextLine()
                }
                Actions.MENU_TITLE -> {
                    _hasMenu.postValue(true)
                    var line = lines[currentLine].line
                    val character = findCharacter(line.first().toString())
                    _currentCharacter.postValue(character)
                    line = line.subSequence(line.indexOf("\"") + 1, line.lastIndexOf("\"")).toString()
                    _currentText.postValue(line)
                }
                Actions.JUMP -> {
                    jump(lines.last().line)
                }
                Actions.RETURN -> {
                    returnEvent.postValue(true)
                }
                else -> {
                    nextLine()
                }
            }
        }
    }

    fun getCurrentText() {
        val lines = getCurrentLines()
        _currentText.postValue(lines[currentLine].line)
    }

    fun jump(label : String){
        _hasMenu.postValue(false)
        currentLine = -1
        currentLabel= label
        nextLine()
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

    fun findCharacter(nameKey : String): Character {
        var character = Character("", "", Color.WHITE)
        scenario.characters.forEach {
            if(it.key == nameKey)
                character = it
        }
        return character
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