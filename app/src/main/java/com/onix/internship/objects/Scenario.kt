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
    PLAY_MUSIC, SCENE, SHOW_WITH, SHOW, ANIMATION,
    MENU, JUMP, CHANGE_TEXT, IF, UPDATE_VARIABLE
}

data class GameVariable(
    val key : String,
    var value : Any)

data class Character(
    val key : String,
    val name : String,
    @ColorInt val color : Int)

data class GameString(
    val action : Actions,
    val line : String)

data class GameLabel(val title : String){
    private val gameStrings = arrayListOf<GameString>()

    fun getGameStrings(): ArrayList<GameString> {
        return gameStrings
    }

    fun addGameString(item : GameString){
        gameStrings.add(item)
    }
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

        list.forEach {
            if(it.contains("label")){
                if(gameLabel.title.isNotEmpty()){ scenario.add(gameLabel) }
                gameLabel = GameLabel(it.substring(it.indexOf(" ") + 1, it.indexOf(":")))
                Log.d("DEBUG", "1")
            }
            else{
                gameLabel.addGameString(getGameString(it))
                Log.d("DEBUG", "3")
            }
        }

        scenario.add(gameLabel)
    }

    private fun getGameString(currentStr : String): GameString {
        val regex = "([\"\'])(?:(?=(\\\\?))\\2.)*?\\1".toRegex() //get text inside quotes

        var action = Actions.CHANGE_TEXT
        var line = currentStr.trim()

        val firstWord = line.split(" ")[0]

        when(true){
            firstWord.contains("play") ->{
                line = regex.find(line)?.groupValues?.getOrNull(0)?.replace("\"", "").toString()
                action = Actions.PLAY_MUSIC
            }
            firstWord.contains("scene") -> {
                line = getTitle(line)
                action = Actions.SCENE
            }
            firstWord.contains("show") -> {
                line = getTitle(line)
                action = Actions.SHOW
            }
            firstWord.contains("with") -> {
                line = getTitle(line)
                action = Actions.ANIMATION
            }
            firstWord.contains("menu") -> {
                action = Actions.MENU
            }
            firstWord.contains("jump")-> {
                line = getTitle(line)
                action = Actions.JUMP
            }
            firstWord.contains("$") -> {
                action = Actions.UPDATE_VARIABLE
            }
            firstWord.contains("if") -> {
                line = getTitle(line)
                action = Actions.IF
            }
            else -> {}
        }

        return GameString(action, line)
    }

    private fun getTitle(line : String): String {
        return line.subSequence(line.indexOf(" ") + 1, line.length)
            .toString().replace(" ", "_")
    }

}