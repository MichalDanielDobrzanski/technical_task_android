package com.michal.technicaltask.presentation.main

import android.app.Application
import com.michal.technicaltask.BuildConfig
import com.michal.time.TimeProvider
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class MainApplication : Application() {

    @Inject
    lateinit var timeProvider: TimeProvider

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        timeProvider.storeAppStartTime()
    }
}