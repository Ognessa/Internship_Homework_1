package com.onix.internship.objects

import android.graphics.Color

enum class NotesColors {
    RED, YELLOW, GREEN, PINK, BLUE, LIGHT_BLUE;
}

fun getColorsList(): Array<out NotesColors>? {
    return NotesColors::class.java.enumConstants
}

fun getColor(color: NotesColors):  Int {
    return when (color) {
        NotesColors.RED -> Color.RED
        NotesColors.YELLOW -> Color.YELLOW
        NotesColors.GREEN -> Color.GREEN
        NotesColors.PINK -> Color.argb(255, 254, 79, 172)
        NotesColors.BLUE -> Color.BLUE
        NotesColors.LIGHT_BLUE -> Color.argb(255, 51, 181, 229)
    }
}