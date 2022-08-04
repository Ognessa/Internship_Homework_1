package com.onix.internship.arch.ext

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import androidx.annotation.RawRes
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.onix.internship.R

@BindingAdapter("circleImage", "placeholder", requireAll = false)
fun ImageView.bindCircleImage(image: String?, placeholder: Drawable?) {
    if (image.isNullOrEmpty()) {
        setImageDrawable(placeholder)
    } else {
        Glide.with(context)
            .load(Uri.parse(image))
            .apply(RequestOptions().circleCrop())
            .placeholder(placeholder)
            .error(placeholder)
            .into(this)
    }
}

@BindingAdapter("gifImage", requireAll = false)
fun ImageView.bindGifView(
    @RawRes resId: Int,
) {
    Glide.with(context)
        .asGif()
        .load(resId)
        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        .listener(object : RequestListener<GifDrawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: com.bumptech.glide.request.target.Target<GifDrawable>?,
                isFirstResource: Boolean
            ): Boolean {
                return false
            }

            override fun onResourceReady(
                resource: GifDrawable,
                model: Any?,
                target: com.bumptech.glide.request.target.Target<GifDrawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                resource.setLoopCount(-1)
                return false
            }
        })
        .into(this)
}

@SuppressLint("SetTextI18n")
@BindingAdapter("userName")
fun AppCompatTextView.bindUserName(name: String?) {
    val userName = if (name.isNullOrBlank()) {
        "Unknown"
    } else name
    text = "$userName:"
}

@BindingAdapter("onRefresh")
fun SwipeRefreshLayout.onRefresh(callback: () -> Unit) {
    setOnRefreshListener {
        callback.invoke()
        isRefreshing = false
    }
}

@BindingAdapter("deviceValue")
fun ImageView.deviceValue(value: String) {
    if (value.contains("https://")) {
        Glide.with(context)
            .load(Uri.parse(value))
            .into(this)
    } else if (value == "ONETIME") {
        Glide.with(context)
            .load("https://www.wrenkitchens.com/blog/wp-content/uploads/2021/12/2022-kitchen-design-trends-dark-kitchen-2048x1366.jpg")
            .into(this)
    } else {
        this.setImageResource(R.drawable.ic_none_image)
    }
}