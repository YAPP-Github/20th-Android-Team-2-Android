package com.yapp.android2.data.service.interceptor

import android.content.SharedPreferences
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class HeaderInterceptor @Inject constructor(private val sharedPreferences: SharedPreferences) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val authorizedHeader = chain.request().newBuilder()
            .addHeader(AUTHORIZED, sharedPreferences.getString(AUTHORIZED,"").orEmpty())


        return chain.proceed(authorizedHeader.build())
    }

    companion object {
        private const val AUTHORIZED = "Authorization"
    }
}
