package com.onix.internship.di

import com.onix.internship.ui.activity.ActivityViewModel
import com.onix.internship.ui.addTask.AddTaskViewModel
import com.onix.internship.ui.folder.FolderViewModel
import com.onix.internship.ui.home.HomeViewModel
import com.onix.internship.ui.main.MainViewModel
import com.onix.internship.ui.tabMenu.TabMenuViewModel
import com.onix.internship.ui.taskList.TaskListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel() }
    viewModel {TabMenuViewModel()}
    viewModel {ActivityViewModel()}
    viewModel { AddTaskViewModel() }
    viewModel { FolderViewModel() }
    viewModel { HomeViewModel() }
    viewModel { TaskListViewModel() }
}