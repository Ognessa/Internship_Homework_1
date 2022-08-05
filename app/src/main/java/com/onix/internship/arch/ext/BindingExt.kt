package com.onix.internship.arch.ext

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
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
import com.onix.internship.entity.SensorSubType
import com.onix.internship.entity.SensorType

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
    }
}

@BindingAdapter("refreshing")
fun SwipeRefreshLayout.refreshing(refreshing: Boolean) {
    isRefreshing = refreshing
}

@BindingAdapter("deviceValue")
fun ImageView.deviceValue(value: String) {
    Glide.with(context)
        .load(Uri.parse(value))
        .placeholder(R.drawable.ic_none_image)
        .error(R.drawable.ic_none_image)
        .into(this)
}

@BindingAdapter("typesList", "callback")
fun Spinner.typesList(array: Array<SensorType>, callback: (SensorType) -> Unit) {
    val list = arrayListOf<String>()
    array.forEach {
        list.add(resources.getString(it.getValue()))
    }

    val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, list)
    this.adapter = adapter

    this.onItemSelectedListener = object : OnItemSelectedListener {

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
            callback.invoke(array[pos])
        }

        override fun onNothingSelected(p0: AdapterView<*>?) {}
    }
}

@BindingAdapter("subtypesList", "callback")
fun Spinner.subtypesList(array: Array<SensorSubType>, callback: (SensorSubType) -> Unit) {
    val list = arrayListOf<String>()
    array.forEach {
        list.add(resources.getString(it.getValue()))
    }
    val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, list)
    this.adapter = adapter

    this.onItemSelectedListener = object : OnItemSelectedListener {

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
            callback.invoke(array[pos])
        }

        override fun onNothingSelected(p0: AdapterView<*>?) {}
    }
}