package com.onix.internship.ui.editor.data

import android.graphics.PointF
import android.os.Parcelable
import com.onix.internship.ui.editor.EditorModeTypes
import kotlinx.parcelize.Parcelize

sealed class UserEditorActions : Parcelable {

    @Parcelize
    data class SimpleDrawAction(
        val startPoint: PointF,
        val endPoint: PointF,
        val brushColor: Int,
        val brushSize: Float,
        val action: EditorModeTypes
    ) : UserEditorActions()

    @Parcelize
    data class FigureDraw(
        val points: List<PointF>,
        val brushSize: Float,
        val brushColor: Int
    ) : UserEditorActions()

    @Parcelize
    data class TextDraw(
        val startPoint: PointF,
        val endPoint: PointF,
        val text: String,
        val textSize: Float,
        val brushColor: Int
    ) : UserEditorActions()
}