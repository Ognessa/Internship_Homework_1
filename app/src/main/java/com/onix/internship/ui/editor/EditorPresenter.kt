package com.onix.internship.ui.editor

import com.onix.internship.ui.editor.view.ImageEditorView

interface EditorPresenter {

    fun onColorPressed()

    fun onRectPressed()

    fun onLinePressed()

    fun onArrowPressed()

    fun onTextPressed()

    fun onUndoPressed(view: ImageEditorView)

    fun onRedoPressed(view: ImageEditorView)

    fun onSavePressed(view: ImageEditorView)
}