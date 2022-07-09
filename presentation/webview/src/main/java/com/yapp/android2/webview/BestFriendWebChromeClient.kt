package com.yapp.android2.webview

import android.webkit.WebChromeClient
import android.webkit.WebView

class BestFriendWebChromeClient private constructor(
    private val onChangeProgress: (Int) -> Unit
) : WebChromeClient() {

    override fun onProgressChanged(view: WebView?, newProgress: Int) {
        onChangeProgress.invoke(newProgress)

        super.onProgressChanged(view, newProgress)
    }

    companion object {
        fun getInstance(onChangeProgress: (Int) -> Unit) = BestFriendWebChromeClient(onChangeProgress)
    }

}
