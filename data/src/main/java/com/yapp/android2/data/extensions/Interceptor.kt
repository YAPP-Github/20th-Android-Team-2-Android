package com.yapp.android2.data.extensions

import com.yapp.android2.data.service.Service
import okhttp3.Interceptor
import okhttp3.OkHttpClient

internal fun createInterceptors(interceptors: List<Interceptor>): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptors(interceptors)
        .apply {
            hostnameVerifier { hostname, _ ->
                hostname?.contentEquals(Service.HOST_NAME) == true
            }
        }
        .build()
}


private fun OkHttpClient.Builder.addInterceptors(interceptor: List<Interceptor>): OkHttpClient.Builder = apply {
    interceptor.forEach(::addInterceptor)
}
