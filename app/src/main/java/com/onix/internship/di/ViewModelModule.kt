package com.onix.internship.di

import com.onix.internship.ui.gallery.GalleryViewModel
import com.onix.internship.ui.gallery.player.GalleryMediaPlayer
import com.onix.internship.ui.gallery.recorder.AudioRecorder
import com.onix.internship.ui.recorder.RecorderViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModels = module {
    viewModel<GalleryViewModel> {
        GalleryViewModel(
            contentResolver = androidApplication().contentResolver,
            audioPlayer = get<GalleryMediaPlayer>()
        )
    }

    viewModel<RecorderViewModel> {
        RecorderViewModel(
            player = get<GalleryMediaPlayer>(),
            recorder = get<AudioRecorder>(),
            context = androidApplication()
        )
    }
}