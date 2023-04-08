package com.onix.internship.ui.gallery

import com.onix.internship.arch.controller.BaseViewModel
import com.onix.internship.arch.router.command.NavDirection
import com.onix.internship.ui.gallery.adapter.GalleryAdapterPresenter
import com.onix.internship.ui.gallery.adapter.model.GalleryAdapterModel

class GalleryViewModel : BaseViewModel(), GalleryAdapterPresenter {

    override fun onImageClick(model: GalleryAdapterModel) {
        navigate(
            NavDirection.Direction(
                GalleryFragmentDirections.actionGalleryFragmentToAvatarEditorFragment(
                    url = model.url
                )
            )
        )
    }
}