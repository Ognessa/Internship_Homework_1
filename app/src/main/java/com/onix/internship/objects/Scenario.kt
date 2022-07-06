package com.onix.internship.objects

import android.content.Context
import android.graphics.Color
import android.util.Log
import androidx.annotation.ColorInt

/**
    PLAY_MUSIC -> play some music
    SCENE -> change scene background
    SHOW_WITH -> change character with animation
    SHOW -> change character without animation
    MENU -> menu with buttons and action
    JUMP -> jump to some label
    CHANGE_TEXT -> change text in dialog/monolog
    IF -> do something if ...
    UPDATE_VARIABLE -> change value of some variable
    */
enum class Actions{
    PLAY_MUSIC, SCENE, SHOW, ANIMATION, MENU,
    MENU_TITLE, JUMP, CHANGE_TEXT, IF, UPDATE_VARIABLE, RETURN
}

data class GameVariable(
    val key : String,
    var value : Any)

data class Character(
    val key : String,
    val name : String,
    @ColorInt val color : Int)

data class GameMenuButton(
    val title : String,
    val jump : String
)

data class GameString(
    val action : Actions,
    val line : String)

data class GameLabel(val title : String){
    private val gameStrings = arrayListOf<GameString>()
    private val gameMenu = arrayListOf<GameMenuButton>()

    fun getGameStrings(): ArrayList<GameString> { return gameStrings }
    fun addGameString(item : GameString){ gameStrings.add(item) }

    fun getGameMenu(): ArrayList<GameMenuButton> { return gameMenu }
    fun addGameMenuButton(item : GameMenuButton){ gameMenu.add(item) }
}

class Scenario(val context : Context) {
    val scenario = mutableListOf<GameLabel>()
    val characters = arrayListOf<Character>()
    val variables = arrayListOf<GameVariable>()

    fun parseScenario(){
        var listOfLines = mutableListOf<String> ()
        context.assets.open("scenario.rpy").bufferedReader().useLines {
            lines->lines.forEach{
                if(it.isNotEmpty())
                    listOfLines.add (it)
            }
        }

        listOfLines = getCharacters(listOfLines)
        listOfLines = getVariables(listOfLines)

        getGameLabels(listOfLines)

        scenario.forEach {
            Log.d("DEBUG", it.title)
            it.getGameStrings().forEach { i ->
                Log.d("DEBUG", "${i.action} : ${i.line}")
            }
            it.getGameMenu().forEach { i ->
                Log.d("DEBUG", "${i.title} -> ${i.jump}")
            }
        }
    }

    private fun getCharacters(list: MutableList<String>): MutableList<String> {
        val index = arrayListOf<Int>()

        list.forEach {
            if(it.contains("Character")){
                val temp = it.split(" ").toList()
                val regex = "([\"\'])(?:(?=(\\\\?))\\2.)*?\\1".toRegex() //get text inside quotes
                val key = temp[1]
                val name = regex.find(temp[3])?.groupValues?.getOrNull(0)?.replace("\"", "")
                val color = Color.parseColor(regex.find(temp[4])?.groupValues?.getOrNull(0)?.replace("\"", ""))

                val character = name?.let { it1 -> Character(key, it1, color) }

                if(character != null) {
                    characters.add(character)
                }
                index.add(list.indexOf(it))
            }
        }

        index.reverse()
        index.forEach {
            list.removeAt(it)
        }
        return list
    }

    private fun getVariables(list: MutableList<String>): MutableList<String> {
        val index = arrayListOf<Int>()

        list.forEach {
            if(it.contains("default")){
                val temp = it.split(" ").toList()
                variables.add(GameVariable(temp[1],temp[3]))
                index.add(list.indexOf(it))
            }
        }

        index.reverse()
        index.forEach { list.removeAt(it) }
        return list
    }

    private fun getGameLabels(list: MutableList<String>){
        var gameLabel = GameLabel("")
        var buttonTitle = ""

        list.forEach {
            if(it.contains("label")){
                if(gameLabel.title.isNotEmpty()){ scenario.add(gameLabel) }
                gameLabel = GameLabel(it.substring(it.indexOf(" ") + 1, it.indexOf(":")))
            }
            else{
                if(gameLabel.getGameStrings().isNotEmpty() && gameLabel.getGameStrings().last().action == Actions.MENU_TITLE) {
                    if(buttonTitle.isEmpty()) {
                        buttonTitle = it.trim()
                        buttonTitle = buttonTitle.subSequence(buttonTitle.indexOf("\"") + 1, buttonTitle.lastIndexOf("\"")).toString()
                    }
                    else{
                        val jump = it.trim().split(" ")[1]
                        gameLabel.addGameMenuButton(GameMenuButton(buttonTitle, jump))
                        buttonTitle = ""
                    }
                }
                else
                    updateGameLabel(gameLabel, it)
            }
        }

        scenario.add(gameLabel)
    }

    private fun updateGameLabel(gameLabel : GameLabel, currentStr : String) {
        val regex = "([\"\'])(?:(?=(\\\\?))\\2.)*?\\1".toRegex() //get text inside quotes

        var line = currentStr.trim()
        val firstWord = line.split(" ")[0]
        lateinit var gameString : GameString

        when(true){
            firstWord.contains("play") ->{
                line = regex.find(line)?.groupValues?.getOrNull(0)?.replace("\"", "").toString()
                gameString = GameString(Actions.PLAY_MUSIC, line)
            }
            firstWord.contains("scene") -> {
                line = getTitle(line)
                gameString = GameString(Actions.SCENE, line)
            }
            firstWord.contains("show") -> {
                line = getTitle(line)
                gameString = GameString(Actions.SHOW, line)
            }
            firstWord.contains("with") -> {
                line = getTitle(line)
                gameString = GameString(Actions.ANIMATION, line)
            }
            firstWord.contains("menu") -> {
                gameString = GameString(Actions.MENU, line)
            }
            firstWord.contains("jump") -> {
                gameString = GameString(Actions.JUMP, line.split(" ")[1])
            }
            firstWord.contains("$") -> {
                gameString = GameString(Actions.UPDATE_VARIABLE, line)
            }
            firstWord.contains("if") -> {
                line = getTitle(line)
                gameString = GameString(Actions.IF, line)
            }
            firstWord.contains("return") -> {
                line = getTitle(line)
                gameString = GameString(Actions.RETURN, line)
            }
            else -> {
                if(gameLabel.getGameStrings().isNotEmpty() && gameLabel.getGameStrings().last().action == Actions.MENU)
                    gameString = GameString(Actions.MENU_TITLE, line)
                else
                    gameString = GameString(Actions.CHANGE_TEXT, line)
            }
        }
        gameLabel.addGameString(gameString)
    }

    private fun getTitle(line : String): String {
        return line.subSequence(line.indexOf(" ") + 1, line.length)
            .toString().replace(" ", "_")
    }

}