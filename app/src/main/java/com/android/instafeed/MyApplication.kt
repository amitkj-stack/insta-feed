package com.android.instafeed

import android.app.Application
import com.android.instafeed.di.appModule
import com.android.instafeed.support.Utils
import com.android.instafeed.support.Utils.isNetworkAvailable
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if (isNetworkAvailable())
            Utils.clearDiskCache(this)
        // Start Koin
        startKoin {
            androidContext(this@MyApplication)
            modules(appModule)
        }
    }
}