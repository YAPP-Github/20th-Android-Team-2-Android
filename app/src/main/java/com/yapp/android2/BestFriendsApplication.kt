package com.yapp.android2

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class BestFriendsApplication : Application() {

    @Inject
    lateinit var activityLogger: ActivityLifecycleLogger

    override fun onCreate() {
        super.onCreate()
        initTimber()
        registerActivityLifecycleCallbacks()
    }

    private fun initTimber() {
        Timber.plant(Timber.DebugTree())
    }

    private fun registerActivityLifecycleCallbacks() {
        registerActivityLifecycleCallbacks(activityLogger)
    }
}
