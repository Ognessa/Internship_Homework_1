package com.onix.internship.ui.editor

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.onix.internship.arch.controller.BaseViewModel
import com.onix.internship.arch.router.command.NavDirection
import com.onix.internship.ui.editor.data.EditorManager
import com.onix.internship.ui.editor.view.ImageEditorView
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException

class EditorViewModel(
    private val context: Context,
    private val editorManager: EditorManager
) : BaseViewModel(), EditorPresenter {

    val model = EditorModel()

    private val _image = MutableLiveData<Bitmap>()
    val image: LiveData<Bitmap> = _image

    init {
        loadBitmap(editorManager.currentEditedImage)
    }

    private fun loadBitmap(url: String) {
        launch {
            val loader = ImageLoader(context)
            val request = ImageRequest.Builder(context)
                .data(url)
                .allowHardware(false) // Disable hardware bitmaps.
                .build()

            val result = (loader.execute(request) as SuccessResult).drawable
            _image.value = (result as BitmapDrawable).bitmap
        }
    }

    fun saveBitmap(bitmap: Bitmap): String? {
        val dir = File(context.filesDir, "temp")
        if (!dir.exists()) {
            dir.mkdir()
        }
        val file = File(dir, "cropped_${System.currentTimeMillis()}.png")
        return try {
            val fos = file.outputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, fos)
            fos.close()
            file.path
        } catch (e: FileNotFoundException) {
            Log.d("DEBUG", "File not found: " + e.message)
            null
        } catch (e: IOException) {
            Log.d("DEBUG", "Error accessing file: " + e.message)
            null
        }
    }

    fun getImage(): String {
        return editorManager.currentEditedImage
    }

    override fun onColorPressed() {
        navigate(
            NavDirection.Direction(
                EditorFragmentDirections.actionEditorFragmentToColorPickerDialogFragment(
                    selectedColor = model.selectedColor.get()
                )
            )
        )
    }

    override fun onRectPressed() {
        setMode(EditorModeTypes.RECTANGLE)
    }

    override fun onLinePressed() {
        setMode(EditorModeTypes.LINE)
    }

    override fun onArrowPressed() {
        setMode(EditorModeTypes.ARROW)
    }

    override fun onDrawPressed() {
        setMode(EditorModeTypes.DRAW)
    }

    override fun onTextPressed() {
        setMode(EditorModeTypes.TEXT)
    }

    private fun setMode(mode: EditorModeTypes) {
        if (model.selectedMode.get() != mode) {
            model.selectedMode.set(mode)
        }
    }

    override fun onUndoPressed(view: ImageEditorView) {
        view.undo()
    }

    override fun onRedoPressed(view: ImageEditorView) {
        view.redo()
    }

    override fun onSavePressed(view: ImageEditorView) {
        val bitmap = view.saveImage()

        val dir = File(context.filesDir, "temp")
        if (!dir.exists()) {
            dir.mkdir()
        }
        val file = File(dir, "cropped_${System.currentTimeMillis()}.png")
        val url = try {
            val fos = file.outputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, fos)
            fos.close()
            file.path
        } catch (e: FileNotFoundException) {
            Log.d("DEBUG", "File not found: " + e.message)
            null
        } catch (e: IOException) {
            Log.d("DEBUG", "Error accessing file: " + e.message)
            null
        }

        navigate(
            NavDirection.Direction(
                EditorFragmentDirections.actionEditorFragmentToResultFragment(url.orEmpty())
            )
        )
    }
}