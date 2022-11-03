package com.onix.internship.arch.ui.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.onix.internship.R

abstract class BaseDialog<T : ViewDataBinding>(
    context: Context,
    @LayoutRes private val contentLayoutID: Int
) : Dialog(context) {

    protected lateinit var binding: T
        private set

    open val isEnableAnimationTransition = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context), contentLayoutID,
            null, false
        )
        setContentView(binding.root)
        setWindowProperties()
    }

    private fun setWindowProperties() {
        window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            if (isEnableAnimationTransition) {
                attributes?.windowAnimations = R.style.DialogAnimation
            }
        }
    }
}