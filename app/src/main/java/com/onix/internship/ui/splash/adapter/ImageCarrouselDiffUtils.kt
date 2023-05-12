package com.onix.internship.ui.splash.adapter

import androidx.recyclerview.widget.DiffUtil

class ImageCarrouselDiffUtils : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}