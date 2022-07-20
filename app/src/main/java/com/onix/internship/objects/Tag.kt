package com.onix.internship.objects

import android.graphics.Color
import kotlin.random.Random


data class Tag(val tag: String){
    var textColor = 0
    var backColor = 0

    fun randomColor(){
        val randomRed = Random.nextInt(80, 180)
        val randomGreen = Random.nextInt(80, 180)
        val randomBlue = Random.nextInt(80, 180)
        textColor = Color.argb(255, randomRed, randomGreen, randomBlue)
        backColor = Color.argb(255, randomRed + 75, randomGreen + 75, randomBlue + 75)
    }
}