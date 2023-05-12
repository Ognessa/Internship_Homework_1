package com.onix.internship.ui.splash.adapter

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.onix.internship.arch.ui.recycler.BaseRecyclerListAdapter
import com.onix.internship.R
import com.onix.internship.arch.ui.recycler.BaseViewHolder
import com.onix.internship.ui.splash.SplashViewModel

class ImageCarrouselAdapter(
    private val viewModel: SplashViewModel
) : BaseRecyclerListAdapter<String>(ImageCarrouselDiffUtils()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<String, out ViewDataBinding> {
        return ImageCarrouselViewHolder(
            binding = inflate(parent, R.layout.item_carrousel_card),
            viewModel = viewModel
        )
    }
}