package com.onix.internship.ui.sensors.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.onix.internship.entity.DeviceData
import com.onix.internship.databinding.CardsItemBinding

class SensorsAdapter(
    private val onSensorClickListener: OnSensorClickListener
) : ListAdapter<DeviceData, SensorsAdapter.ViewHolder>(DiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onSensorClickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: CardsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DeviceData, onSensorClickListener: OnSensorClickListener) {
            binding.cardsItem = item

            binding.deleteSensorButton.setOnClickListener {
                onSensorClickListener.deleteSensor(item)
            }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CardsItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<DeviceData>() {
        override fun areItemsTheSame(oldItem: DeviceData, newItem: DeviceData): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: DeviceData, newItem: DeviceData): Boolean {
            return oldItem.room == newItem.room
        }
    }
}