package com.onix.internship.objects

enum class NotesColors {
    RED, YELLOW, GREEN, PINK, BLUE, LIGHT_BLUE;
}

fun getColorsList(): ArrayList<NotesColors> {
    val list = arrayListOf<NotesColors>()
    NotesColors::class.java.enumConstants?.forEach {
        list.add(it)
    }
    return list
}