package com.onix.internship.arch.ui.view.ktx

import android.content.res.ColorStateList
import android.widget.Button
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.TextViewCompat
import androidx.databinding.BindingAdapter
import com.onix.internship.R

@BindingAdapter("selected")
fun Button.setSelectedButton(selected: Boolean) {
    val color = if (selected) {
        ResourcesCompat.getColor(resources, R.color.orange, null)
    } else {
        ResourcesCompat.getColor(resources, R.color.black, null)
    }

    setTextColor(color)
    TextViewCompat.setCompoundDrawableTintList(this, ColorStateList.valueOf(color))
}

@BindingAdapter("drawTint")
fun Button.setDrawableTint(drawTint: Int?) {
    if (drawTint != null) {
        TextViewCompat.setCompoundDrawableTintList(this, ColorStateList.valueOf(drawTint))
    }
}