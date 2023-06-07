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
        val action: EditorModeTypes
    ) : UserEditorActions()
}