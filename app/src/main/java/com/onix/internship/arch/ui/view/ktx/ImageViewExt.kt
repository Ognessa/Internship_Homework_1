package com.onix.internship.arch.ui.view.ktx

import android.widget.ImageView
import androidx.annotation.RawRes
import androidx.databinding.BindingAdapter
import com.onix.internship.R
import com.onix.internship.arch.ui.view.model.IconProvider
import com.onix.internship.entity.local.PersonDataEntity

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

@BindingAdapter("personData", requireAll = false)
fun ImageView.getAgeImage(personData: PersonDataEntity) {
    val age = personData.age
    val list = listOf('a', 'e', 'i', 'o', 'u', 'y')
    if (personData.name.isNotEmpty()) {
        if (list.contains(personData.name.last())) {
            when (true) {
                (age < 10) -> setImageResource(R.drawable.girt1)
                (age in 10..17) -> setImageResource(R.drawable.girl2)
                (age in 18..29) -> setImageResource(R.drawable.girl3)
                (age in 30..59) -> setImageResource(R.drawable.girl4)
                else -> setImageResource(R.drawable.girl5)
            }
        } else {
            when (true) {
                (age < 10) -> setImageResource(R.drawable.boy1)
                (age in 10..17) -> setImageResource(R.drawable.boy2)
                (age in 18..29) -> setImageResource(R.drawable.boy3)
                (age in 30..59) -> setImageResource(R.drawable.boy4)
                else -> setImageResource(R.drawable.boy5)
            }
        }
    }
}