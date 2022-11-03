package com.onix.internship.arch.component.error

import com.onix.internship.domain.entities.failure.Failure

interface ErrorHandler {

    fun parseFailure(failure: Failure)

}