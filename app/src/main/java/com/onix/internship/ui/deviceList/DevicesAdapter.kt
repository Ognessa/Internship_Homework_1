package com.onix.internship.ui.deviceList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.onix.internship.R
import com.onix.internship.arch.adapter.BaseRecyclerAdapter
import com.onix.internship.databinding.DeviceItemBinding
import com.onix.internship.objects.DeviceData

class DeviceHolder(val binding : DeviceItemBinding) : RecyclerView.ViewHolder(binding.root){
    fun bind(it : DeviceData){
        binding.deviceData = it
    }
}

class DevicesAdapter : BaseRecyclerAdapter<DeviceHolder, DeviceData>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceHolder {
        return from(parent)
    }

    override fun onBindViewHolder(holder: DeviceHolder, position: Int) {
        holder.bind(adapterItems[position])
    }

    companion object{
        fun from(parent: ViewGroup) : DeviceHolder{
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.device_item, parent, false)
            val binding = DeviceItemBinding.bind(view)
            return DeviceHolder(binding)
        }
    }
}