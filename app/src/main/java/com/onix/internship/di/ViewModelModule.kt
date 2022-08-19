package com.onix.internship.di

import com.onix.internship.ui.advancedSearch.AdvancedSearchViewModel
import com.onix.internship.ui.main.MainViewModel
import com.onix.internship.ui.moreInfo.MoreInfoViewModel
import com.onix.internship.ui.simpleSearch.SimpleSearchViewModel
import com.onix.internship.ui.soundList.SoundListViewModel
import com.onix.internship.ui.tabMenu.TabMenuViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { TabMenuViewModel(get(), get(), get(), get()) }
    viewModel { SimpleSearchViewModel(get()) }
    viewModel { AdvancedSearchViewModel(get()) }
    viewModel { SoundListViewModel(get(), get(), get()) }
    viewModel { MoreInfoViewModel(get(), get(), get()) }
    viewModel { MainViewModel() }
}