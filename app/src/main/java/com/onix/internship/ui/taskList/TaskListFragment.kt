package com.onix.internship.ui.taskList

import com.onix.internship.R
import com.onix.internship.arch.BaseFragment
import com.onix.internship.databinding.TaskListFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class TaskListFragment : BaseFragment<TaskListFragmentBinding>(R.layout.task_list_fragment){

    override val viewModel : TaskListViewModel by viewModel()

}