package com.onix.internship.ui.taskList

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.onix.internship.R
import com.onix.internship.arch.BaseFragment
import com.onix.internship.databinding.TaskListFragmentBinding
import com.onix.internship.databinding.WeekDaysItemBinding
import com.onix.internship.objects.WeekDayItem
import org.koin.androidx.viewmodel.ext.android.viewModel

class TaskListFragment : BaseFragment<TaskListFragmentBinding>(R.layout.task_list_fragment){

    override val viewModel : TaskListViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        binding.viewModel = viewModel

        generateWeekDaysList(binding.llWeekDaysList,  viewModel.getWeekDaysList())

        return view
    }

    private fun generateWeekDaysList(llWeekDaysList : LinearLayout, list : ArrayList<WeekDayItem>){
        llWeekDaysList.removeAllViews()

        list.forEach {
            val view = LayoutInflater.from(requireContext()).inflate(R.layout.week_days_item, null)
            val binding = WeekDaysItemBinding.bind(view)

            binding.tvWeekDayTitle.text = it.weekDay
            binding.tvWeekDayNum.text = it.dayNum.toString()

            if(it.weekDay == viewModel.currentDay){
                binding.llItemContainer.background = resources.getDrawable(R.drawable.background, null)
                binding.tvWeekDayTitle.setTextColor(Color.WHITE)
                binding.tvWeekDayNum.setTextColor(Color.WHITE)
            }

            llWeekDaysList.addView(view)
        }
    }

}