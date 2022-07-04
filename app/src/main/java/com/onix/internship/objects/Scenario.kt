package com.onix.internship.objects

import android.content.Context

class Scenario(val context : Context) {
    val scenario = mutableListOf<String>()
    val currentLine = 0
    var book = false

    fun parseScenario(){
        val listOfLines = mutableListOf<String> ()
        context.assets.open("scenario.rpy").bufferedReader().useLines {
            lines->lines.forEach{
                if(it.isNotEmpty())
                    listOfLines.add (it)
            }
        }
        scenario.addAll(listOfLines)
    }

}