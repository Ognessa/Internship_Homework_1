package com.onix.internship.di

import com.onix.internship.arch.component.error.DefaultFailureHandler
import com.onix.internship.arch.component.error.ErrorHandler
import com.onix.internship.domain.component.logger.Logger
import com.onix.internship.ui.editor.data.EditorManager
import org.koin.dsl.module

val defaultPresentation = module {
    factory<ErrorHandler> { DefaultFailureHandler(logger = get<Logger>()) }
    single<EditorManager> { EditorManager() }
}