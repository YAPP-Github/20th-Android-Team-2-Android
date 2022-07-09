package com.yapp.android2.webview

import android.graphics.Bitmap
import android.webkit.CookieManager
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient

class BestFriendWebViewClient private constructor(
    private val onPageStarted: () -> Unit = {}
) : WebViewClient() {

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)

        onPageStarted.invoke()
    }

    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        val url = request?.url.toString()

        view?.loadUrl(url)

        return true

    }

    companion object {
        fun getInstance(onPageStarted: () -> Unit) = BestFriendWebViewClient(onPageStarted)
    }
}
