package com.yapp.android2.deeplink

import com.airbnb.deeplinkdispatch.DeepLinkSpec
import com.yapp.android2.BuildConfig

@DeepLinkSpec(
    prefix = [
        BuildConfig.SCHEME_PREFIX + "://"
    ]
)
@kotlin.annotation.Retention(AnnotationRetention.BINARY)
annotation class DeepLinkPrefixSpec(vararg val value: String)
