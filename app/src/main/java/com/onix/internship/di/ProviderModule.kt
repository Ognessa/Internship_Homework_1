package com.onix.internship.di

import com.onix.internship.arch.provider.TextResProvider
import com.onix.internship.data.download.DownloadManager
import com.onix.internship.players.RecordingsPlayer
import com.onix.internship.retrofit.Network
import com.onix.internship.retrofit.NetworkFactory
import com.onix.internship.retrofit.NetworkService
import com.onix.internship.ui.moreInfo.MoreInfoModel
import com.onix.internship.ui.tabMenu.SearchModel
import org.koin.dsl.module

val providerModule = module {
    single { TextResProvider(get()) }
    single { SearchModel() }
    single { MoreInfoModel() }

    single { RecordingsPlayer() }

    single { DownloadManager(get()) }

    single { Network(get()) }
    single { NetworkFactory() }
    single { get<NetworkFactory>().createService(NetworkService::class.java) }
}