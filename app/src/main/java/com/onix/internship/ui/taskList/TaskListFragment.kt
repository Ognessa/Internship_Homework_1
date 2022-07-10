package com.onix.internship.ui.taskList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.onix.internship.R
import com.onix.internship.arch.BaseFragment
import com.onix.internship.databinding.TaskListFragmentBinding
import com.onix.internship.objects.WeekDayItem
import org.koin.androidx.viewmodel.ext.android.viewModel

class TaskListFragment : BaseFragment<TaskListFragmentBinding>(R.layout.task_list_fragment){

    override val viewModel : TaskListViewModel by viewModel()

    val list = arrayListOf<WeekDayItem>(
        WeekDayItem("Monday", 12),
        WeekDayItem("Tuesday", 13),
        WeekDayItem("Wednesday",14),
        WeekDayItem("Thursday", 15),
        WeekDayItem("Friday", 16),
        WeekDayItem("Saturday", 17),
        WeekDayItem("Sunday", 18)
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        val adapter = RVWeekDaysAdapter()
        adapter.setContent(list)

        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.orientation = LinearLayout.HORIZONTAL

        binding.rvWeekDaysList.layoutManager = layoutManager
        binding.rvWeekDaysList.adapter = adapter

        return view
    }

}