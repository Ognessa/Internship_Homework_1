package com.onix.internship.di

import com.onix.internship.ui.grid.GridDadViewModel
import com.onix.internship.ui.linear.LinearDADViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModels = module {
    viewModel { LinearDADViewModel() }
    viewModel { GridDadViewModel() }
}