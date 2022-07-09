package com.yapp.android2.webview

import android.os.Bundle
import android.webkit.WebSettings
import com.best.friends.core.BaseActivity
import com.best.friends.core.ui.gone
import com.best.friends.core.ui.visible
import com.yapp.android2.domain.key.POLICY_WEB_URL_KEY
import com.yapp.android2.domain.url.WebURL
import com.yapp.android2.webview.databinding.ActivityWebViewBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class WebViewActivity : BaseActivity<ActivityWebViewBinding>(R.layout.activity_web_view) {

    @Inject
    @Named(POLICY_WEB_URL_KEY)
    lateinit var baseUrl: WebURL

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        toolbarInit()
        webViewInit()
    }

    private fun toolbarInit() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = getString(R.string.policy_title)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        binding.toolbar.setNavigationOnClickListener {
            clearCookie()
            onBackPressed()
        }
    }

    private fun clearCookie() {
        binding.webView.clearCache(true)
    }

    private fun webViewInit() {

        binding.webView.webViewClient = BestFriendWebViewClient.getInstance(
            onPageStarted = { binding.progressBar.visible() },
        )


        binding.webView.webChromeClient = BestFriendWebChromeClient.getInstance { progress ->
            binding.progressBar.progress = progress

            if(progress == 100) { binding.progressBar.gone() }
        }

        webViewSetting()

        binding.webView.loadUrl(baseUrl.url)
    }

    private fun webViewSetting() {
        with(binding.webView.settings) {
            builtInZoomControls = true
            domStorageEnabled = true
            javaScriptEnabled = true
            loadWithOverviewMode = true
            cacheMode = WebSettings.LOAD_NO_CACHE
            setSupportZoom(false)
        }
    }

}
