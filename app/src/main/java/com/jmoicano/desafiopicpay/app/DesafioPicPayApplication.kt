package com.jmoicano.desafiopicpay.app

import android.app.Application
import com.jmoicano.desafiopicpay.injection.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class DesafioPicPayApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@DesafioPicPayApplication)
            modules(appModules)
        }
    }
}