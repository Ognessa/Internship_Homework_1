package com.onix.internship.ui.gallery.recorder

import java.io.File

interface AudioRecorder {
    fun start(outputFile: File)
    fun stop()

    fun setRecorderListener(action: (Long) -> Unit)
}