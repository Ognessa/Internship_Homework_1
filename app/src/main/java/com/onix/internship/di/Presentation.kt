package com.onix.internship.di

import com.onix.internship.arch.component.error.DefaultFailureHandler
import com.onix.internship.arch.component.error.ErrorHandler
import com.onix.internship.domain.component.logger.Logger
import com.onix.internship.ui.gallery.player.GalleryMediaPlayer
import com.onix.internship.ui.gallery.player.GalleryMediaPlayerImpl
import com.onix.internship.ui.gallery.recorder.AudioRecorder
import com.onix.internship.ui.gallery.recorder.AudioRecorderImpl
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val defaultPresentation = module {
    factory<ErrorHandler> { DefaultFailureHandler(logger = get<Logger>()) }
    single<GalleryMediaPlayer> { GalleryMediaPlayerImpl() }
    single<AudioRecorder> {
        AudioRecorderImpl(
            context = androidApplication()
        )
    }
}