package com.eggdevs.cryptocosmos.di

import com.eggdevs.cryptocosmos.core.data.networking.HttpClientFactory
import com.eggdevs.cryptocosmos.crypto.data.networking.KtorRemoteCoinDataSource
import com.eggdevs.cryptocosmos.crypto.domain.RemoteCoinDataSource
import com.eggdevs.cryptocosmos.crypto.presentation.coin_list.CoinListViewModel
import io.ktor.client.engine.cio.CIO
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    single { HttpClientFactory.create(CIO.create()) }
    singleOf(::KtorRemoteCoinDataSource).bind<RemoteCoinDataSource>()
    viewModelOf(::CoinListViewModel)
}