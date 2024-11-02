package com.eggdevs.cryptocosmos

import android.app.Application
import com.eggdevs.cryptocosmos.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class CryptoCosmosApp: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CryptoCosmosApp)
            androidLogger()

            modules(appModule)
        }
    }
}