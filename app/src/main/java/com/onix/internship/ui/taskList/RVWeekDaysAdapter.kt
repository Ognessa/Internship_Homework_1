package com.onix.internship.ui.taskList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.onix.internship.R
import com.onix.internship.arch.adapter.BaseRecyclerAdapter
import com.onix.internship.databinding.WeekDaysItemBinding
import com.onix.internship.objects.WeekDayItem

class RVWeekDaysAdapter : BaseRecyclerAdapter<RVWeekDaysAdapter.WeekDaysViewHolder, WeekDayItem>() {

    class WeekDaysViewHolder(val binding: WeekDaysItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(it : WeekDayItem){
            binding.tvWeekDayTitle.text = it.weekDay
            binding.tvWeekDayNum.text = it.dayNum.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeekDaysViewHolder {
        return from(parent)
    }

    override fun onBindViewHolder(holder: WeekDaysViewHolder, position: Int) {
        holder.bind(adapterItems[position])
    }

    companion object{
        fun from(parent: ViewGroup) : WeekDaysViewHolder{
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.week_days_item, parent, false)
            val binding = WeekDaysItemBinding.bind(view)
            return WeekDaysViewHolder(binding)
        }
    }

}