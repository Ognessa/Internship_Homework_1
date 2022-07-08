package com.onix.internship.ui.game

import android.graphics.Color
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.onix.internship.arch.BaseViewModel
import com.onix.internship.arch.lifecycle.SingleLiveEvent
import com.onix.internship.objects.*

class GameViewModel(private val scenario: Scenario, val player: MusicPlayer) : BaseViewModel() {

    //Character name and color
    private val _currentCharacter = MutableLiveData<Character>()
    val currentCharacter: LiveData<Character> get() = _currentCharacter

    //Just text
    private val _currentText = MutableLiveData<String>()
    val currentText: LiveData<String> get() = _currentText

    //scene background
    private val _currentScene = MutableLiveData<String>()
    val currentScene: LiveData<String> get() = _currentScene

    //character image
    private val _currentCharacterImage = MutableLiveData<String>()
    val currentCharacterImage: LiveData<String> get() = _currentCharacterImage

    //return to main menu
    val returnEvent = SingleLiveEvent<Boolean>()

    //show dialog menu
    private val _hasMenu = MutableLiveData<Boolean>()
    val hasMenu: LiveData<Boolean> get() = _hasMenu

    //list with scenario of this story
    private val data = scenario.scenario

    init {
        _currentScene.postValue(scenario.currentScene)
        _currentCharacterImage.postValue(scenario.currentCharacterImage)
        Log.d(
            "DEBUG",
            "${_currentCharacterImage.value} ${scenario.currentLine} ${scenario.currentLabel}"
        )
        nextLine()
    }

    fun nextLine() {
        scenario.currentLine++
        val currentLine = scenario.currentLine
        val lines = getCurrentLines()
        var line = lines[currentLine].line

        when (lines[currentLine].action) {
            Actions.PLAY_MUSIC -> {
                player.playMusic()
                nextLine()
            }
            Actions.CHANGE_TEXT -> {
                val character = findCharacter(line.first().toString())
                _currentCharacter.postValue(character)
                line = line.subSequence(line.indexOf("\"") + 1, line.lastIndexOf("\"")).toString()
                _currentText.postValue(line)
            }
            Actions.SCENE -> {
                _currentScene.postValue(line)
                _currentCharacterImage.postValue(null)
                nextLine()
            }
            Actions.SHOW -> {
                _currentCharacterImage.postValue(line)
                nextLine()
            }
            Actions.MENU_TITLE -> {
                _hasMenu.postValue(true)
                val character = findCharacter(line.first().toString())
                _currentCharacter.postValue(character)
                line = line.subSequence(line.indexOf("\"") + 1, line.lastIndexOf("\"")).toString()
                _currentText.postValue(line)
            }
            Actions.JUMP -> {
                jump(lines.last().line)
            }
            Actions.RETURN -> {
                clearData()
                player.stopMusic()
                returnEvent.postValue(true)
            }
            else -> {
                nextLine()
            }
        }
    }

    fun jump(label: String) {
        _hasMenu.postValue(false)
        scenario.currentLine = -1
        scenario.currentLabel = label
        nextLine()
    }

    private fun getCurrentLines(): ArrayList<GameString> {
        val lines = arrayListOf<GameString>()
        data.forEach {
            if (it.title == scenario.currentLabel) {
                lines.addAll(it.getGameStrings())
            }
        }
        return lines
    }

    private fun findCharacter(nameKey: String): Character {
        var character = Character("", "", Color.WHITE)
        scenario.characters.forEach {
            if (it.key == nameKey)
                character = it
        }
        return character
    }

    fun getMenuButtons(): ArrayList<GameMenuButton> {
        val buttonsList = arrayListOf<GameMenuButton>()
        data.forEach {
            if (it.title == scenario.currentLabel) {
                buttonsList.addAll(it.getGameMenu())
                Log.d("DEBUG", "Success, ${it.title}")
            }
        }
        return buttonsList
    }

    fun savePosition() {
        if (_currentScene.value.isNullOrEmpty())
            scenario.currentScene = ""
        else
            scenario.currentScene = _currentScene.value.toString()

        if (_currentCharacterImage.value.isNullOrEmpty())
            scenario.currentCharacterImage = ""
        else
            scenario.currentCharacterImage = _currentCharacterImage.value.toString()
    }

    private fun clearData() {
        scenario.currentLine = -1
        scenario.currentLabel = scenario.scenario.first().title
    }
}