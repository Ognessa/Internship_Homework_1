package com.onix.internship.ui.addTask

import com.onix.internship.R
import com.onix.internship.arch.BaseFragment
import com.onix.internship.databinding.AddTaskFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddTaskFragment : BaseFragment<AddTaskFragmentBinding>(R.layout.add_task_fragment){

    override val viewModel: AddTaskViewModel by viewModel()

}