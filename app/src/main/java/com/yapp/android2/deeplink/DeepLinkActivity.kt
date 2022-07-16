package com.yapp.android2.deeplink

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.deeplinkdispatch.BaseDeepLinkDelegate
import com.airbnb.deeplinkdispatch.DeepLinkHandler

@DeepLinkHandler(
    value = [
        DeepLinkModule::class
    ]
)
class DeepLinkActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val deepLinkDelegate = BaseDeepLinkDelegate(
            listOf(
            )
        )

        deepLinkDelegate.dispatchFrom(activity = this@DeepLinkActivity)
        finish()
    }
}
