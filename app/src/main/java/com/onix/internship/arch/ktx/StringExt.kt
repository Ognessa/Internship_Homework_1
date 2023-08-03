package com.onix.internship.arch.ktx

import androidx.core.text.isDigitsOnly

fun String.formatDuration(): String {
    return if (this.isDigitsOnly()) {
        this.toLong().formatDuration()
    } else "xx:xx"
}

fun String.isTimerIncorrect(): Boolean {
    return this == "xx:xx"
}

fun Long.formatDuration(): String {
    val duration = this
    val minutes = duration / 60000
    val seconds = (duration - minutes * 60000) / 1000

    return when {
        minutes < 10 && seconds < 10 -> "0$minutes:0${seconds}"
        seconds < 10 -> "$minutes:0${seconds}"
        minutes < 10 -> "0$minutes:${seconds}"
        else -> "$minutes:${seconds}"
    }
}