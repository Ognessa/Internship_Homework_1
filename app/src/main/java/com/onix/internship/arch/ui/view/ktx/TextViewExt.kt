package com.onix.internship.arch.ui.view.ktx

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.onix.internship.arch.ktx.getString
import com.onix.internship.arch.ui.view.model.TextProvider

@BindingAdapter("setText")
fun TextView.setText(textProvider: TextProvider?) {
    textProvider?.let { text = textProvider.getString(context) }

}