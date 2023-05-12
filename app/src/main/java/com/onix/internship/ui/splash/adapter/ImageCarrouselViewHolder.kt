package com.onix.internship.ui.splash.adapter

import androidx.databinding.ViewDataBinding
import com.onix.internship.arch.ui.recycler.BaseViewHolder
import com.onix.internship.arch.ui.view.ktx.loadImage
import com.onix.internship.arch.ui.view.model.IconProvider
import com.onix.internship.databinding.ItemCarrouselCardBinding
import com.onix.internship.ui.splash.SplashViewModel

class ImageCarrouselViewHolder(
    binding: ViewDataBinding,
    private val viewModel: SplashViewModel
) : BaseViewHolder<String, ItemCarrouselCardBinding>(binding) {
    override fun bindView(position: Int) {
        binding.model = item
        binding.viewModel = viewModel

        binding.image.loadImage(item)
    }
}