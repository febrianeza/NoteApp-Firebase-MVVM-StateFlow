package com.ezafebrian.firebasemvvmstateflow.ui

import android.app.Application
import com.ezafebrian.firebasemvvmstateflow.BuildConfig
import timber.log.Timber

class BaseApp : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}