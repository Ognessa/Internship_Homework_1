package com.onix.internship.arch.ui.view.ktx

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.google.android.material.card.MaterialCardView

@BindingAdapter("width")
fun ConstraintLayout.setWidth(width: Float) {
    val params = this.layoutParams
    params.width = width.toInt()
    this.layoutParams = params
}

@BindingAdapter("height")
fun MaterialCardView.setHeight(height: Float) {
    val params = this.layoutParams
    params.height = height.toInt()
    this.layoutParams = params
}