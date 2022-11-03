package com.onix.internship.arch.controller

import com.onix.internship.arch.router.command.Command
import com.onix.internship.arch.ui.view.model.TextProvider
import com.onix.internship.domain.entities.failure.Failure

interface Controller {
    fun showMessage(message: TextProvider)

    fun setLoading(boolean: Boolean)

    fun navigate(command: Command)

    fun handleFailure(failure: Failure)

}