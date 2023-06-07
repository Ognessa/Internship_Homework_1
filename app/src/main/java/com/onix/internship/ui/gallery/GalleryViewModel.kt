package com.onix.internship.ui.gallery

import com.onix.internship.arch.controller.BaseViewModel
import com.onix.internship.arch.router.command.NavDirection
import com.onix.internship.ui.editor.data.EditorManager
import com.onix.internship.ui.gallery.adapter.GalleryAdapterPresenter

class GalleryViewModel(
    private val editorManager: EditorManager
) : BaseViewModel(), GalleryAdapterPresenter {

    override fun onImageClick(url: String) {
        editorManager.currentEditedImage = url
        navigate(
            NavDirection.Direction(
                GalleryFragmentDirections.actionSplashFragmentToEditorFragment()
            )
        )
    }
}