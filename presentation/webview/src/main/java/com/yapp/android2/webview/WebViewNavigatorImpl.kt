package com.yapp.android2.webview

import android.content.Context
import android.content.Intent
import com.best.friends.navigator.WebViewNavigator
import javax.inject.Inject

class WebViewNavigatorImpl @Inject constructor() : WebViewNavigator {
    override fun intent(context: Context): Intent {
        return Intent(context, WebViewActivity::class.java)
    }
}
