package com.yapp.android2.data.extensions

import okhttp3.Interceptor
import okhttp3.OkHttpClient

internal fun createInterceptors(interceptors: List<Interceptor>): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptors(interceptors)
        .build()
}


private fun OkHttpClient.Builder.addInterceptors(interceptor: List<Interceptor>): OkHttpClient.Builder = apply {
    interceptor.forEach(::addInterceptor)
}
