package com.yapp.android2.deeplink

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.deeplinkdispatch.BaseDeepLinkDelegate
import com.airbnb.deeplinkdispatch.DeepLinkHandler
import com.best.friends.home.MainDeepLinkModule
import com.best.friends.home.MainDeepLinkModuleRegistry
import com.best.friends.login.LoginDeepLinkModule
import com.best.friends.login.LoginDeepLinkModuleRegistry
import com.best.friends.notification.NotificationDeepLinkModule
import com.best.friends.notification.NotificationDeepLinkModuleRegistry

@DeepLinkHandler(
    value = [
        AbstractDeepLinkModule::class,
        MainDeepLinkModule::class,
        NotificationDeepLinkModule::class,
        LoginDeepLinkModule::class
    ]
)
class DeepLinkActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val deepLinkDelegate = BaseDeepLinkDelegate(
            listOf(
                AbstractDeepLinkModuleRegistry(),
                MainDeepLinkModuleRegistry(),
                NotificationDeepLinkModuleRegistry(),
                LoginDeepLinkModuleRegistry()
            )
        )

        deepLinkDelegate.dispatchFrom(activity = this@DeepLinkActivity)
        finish()
    }
}
