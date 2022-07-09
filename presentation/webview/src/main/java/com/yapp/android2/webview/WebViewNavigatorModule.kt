package com.yapp.android2.webview

import com.best.friends.navigator.WebViewNavigator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class WebViewNavigatorModule {

    @Binds
    abstract fun bindWebViewActivity(navigator: WebViewNavigatorImpl) : WebViewNavigator
}
