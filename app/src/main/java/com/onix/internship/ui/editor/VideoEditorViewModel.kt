package com.onix.internship.ui.editor

import com.onix.internship.arch.controller.BaseViewModel

class VideoEditorViewModel : BaseViewModel(), VideoEditorPresenter {

    val model = VideoEditorModel()

    override fun onColorPressed() {
        //TODO open color palete
    }

    override fun onRectPressed() {
        model.selectedMode.set(VideoEditorModeTypes.RECTANGLE)
    }

    override fun onLinePressed() {
        model.selectedMode.set(VideoEditorModeTypes.LINE)
    }

    override fun onArrowPressed() {
        model.selectedMode.set(VideoEditorModeTypes.ARROW)
    }

    override fun onDrawPressed() {
        model.selectedMode.set(VideoEditorModeTypes.DRAW)
    }

    override fun onTextPressed() {
        model.selectedMode.set(VideoEditorModeTypes.TEXT)
    }
}