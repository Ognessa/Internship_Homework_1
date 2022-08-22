package com.onix.internship.arch.ext

import android.net.Uri
import android.view.View
import android.widget.*
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.onix.internship.R

@BindingAdapter("loadUrlImage")
fun ImageView.loadUrlImage(image: String?) {
    val url = "https:$image"
    Glide.with(context)
        .load(Uri.parse(url))
        .placeholder(R.drawable.ic_image_placeholder)
        .error(R.drawable.ic_image_placeholder)
        .into(this)
}

@BindingAdapter("listData", "callback")
fun Spinner.setListData(array : Array<out String>, callback: (String) -> Unit){
    val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, array.toList())
    this.adapter = adapter

    this.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
            callback.invoke(array[pos])
        }

        override fun onNothingSelected(p0: AdapterView<*>?) {}
    }
}

@BindingAdapter("fileType")
fun TextView.setFileType(fileName : String){
    if(fileName.contains('.')){
        val type = fileName.subSequence(fileName.lastIndexOf('.'), fileName.length)
        this.text = type
    } else {
        this.text = fileName
    }
}