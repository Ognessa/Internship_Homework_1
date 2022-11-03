package com.onix.internship.arch.ui.view.ktx

import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import coil.load
import com.onix.internship.R

fun ImageView.loadImage(
    url: String?,
) = load(url) {
    crossfade(true)
    error(R.drawable.placeholder)
}

fun ImageView.loadImage(
    uri: Uri?,
) = load(uri) {
    crossfade(true)
    error(R.drawable.placeholder)
    placeholder(R.drawable.placeholder)
}

fun ImageView.loadImage(
    @DrawableRes resourceId: Int,
) = load(resourceId) {
    crossfade(true)
    error(R.drawable.placeholder)
    placeholder(R.drawable.placeholder)
}

fun ImageView.loadImage(
    drawable: Drawable,
) = load(drawable) {
    crossfade(true)
    error(R.drawable.placeholder)
    placeholder(R.drawable.placeholder)
}

fun ImageView.loadGif(
    @RawRes id: Int,
) = load(id)