package com.onix.internship.arch.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

@SuppressWarnings("unused")
abstract class BaseRecyclerListAdapter<T : Any> : ListAdapter<T, BaseViewHolder<T, out ViewDataBinding>>{
    var item : List<T> = arrayListOf()
        set(value) {
            field = value
            submitList(value)
        }

    constructor(diffCallback : DiffUtil.ItemCallback<T>) : super(diffCallback)
    constructor(config : AsyncDifferConfig<T>) : super(config)

    protected fun inflate(parent : ViewGroup, @LayoutRes contentLayoutId : Int) : ViewDataBinding{
        return DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), contentLayoutId, parent, false
        )
    }

    override fun onViewAttachedToWindow(holder: BaseViewHolder<T, out ViewDataBinding>) {
        holder.onAttachToWindow()
        super.onViewAttachedToWindow(holder)
    }

    override fun onViewDetachedFromWindow(holder: BaseViewHolder<T, out ViewDataBinding>) {
        holder.onDetachFromWindow()
        super.onViewDetachedFromWindow(holder)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<T, out ViewDataBinding>, position: Int) {
        holder.bindView(position, getItem(position))
    }
}
