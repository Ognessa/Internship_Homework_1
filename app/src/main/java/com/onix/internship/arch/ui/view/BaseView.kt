package com.onix.internship.arch.ui.view

import com.onix.internship.arch.ui.view.model.TextProvider
import com.onix.internship.domain.entities.failure.Failure

interface BaseView {
    fun displayError(failure: Failure)

    fun showMessage(textProvider: TextProvider)
}