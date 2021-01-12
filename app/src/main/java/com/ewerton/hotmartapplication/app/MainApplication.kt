package com.ewerton.hotmartapplication.app

import android.app.Application
import com.ewerton.hotmartapplication.di.appComponent
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

open class MainApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        initiateKoin()
    }
    private fun initiateKoin() {
        startKoin{
            androidContext(this@MainApplication)
            modules(provideDependency())
        }
    }

    open fun provideDependency() = appComponent
}