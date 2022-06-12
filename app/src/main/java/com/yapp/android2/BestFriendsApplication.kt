package com.yapp.android2

import android.app.Application
import com.best.friends.login.R
import com.kakao.sdk.common.KakaoSdk
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
        initKakaoSdk()
        registerActivityLifecycleCallbacks()
    }

    private fun initTimber() {
        Timber.plant(Timber.DebugTree())
    }

    private fun initKakaoSdk() {
        KakaoSdk.init(this, getString(R.string.kakao_native_app_key))
    }

    private fun registerActivityLifecycleCallbacks() {
        registerActivityLifecycleCallbacks(activityLogger)
    }
}
