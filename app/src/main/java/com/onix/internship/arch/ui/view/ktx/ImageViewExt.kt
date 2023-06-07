package com.onix.internship.arch.ui.view.ktx

import android.widget.ImageView
import androidx.annotation.RawRes
import androidx.databinding.BindingAdapter
import com.onix.internship.arch.ui.view.model.IconProvider

@BindingAdapter("setImage")
fun ImageView.setImage(iconProvider: IconProvider?) {
    when (iconProvider) {
        is IconProvider.Drawable -> setImageDrawable(iconProvider.icon)
        is IconProvider.Url -> loadImage(iconProvider.url)
        is IconProvider.ResIcon -> loadImage(iconProvider.icon)
        else -> setImageDrawable(null)
    }
}

@BindingAdapter("gifImage")
fun ImageView.gifImage(@RawRes gifImageId: Int) {
    loadGif(gifImageId)
}

@BindingAdapter("backgroundColor")
fun ImageView.setBackgroundSelectedColor(backgroundColor: Int){
    this.setBackgroundColor(backgroundColor)
}