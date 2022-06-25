package com.yapp.android2.data.service.interceptor

import android.content.Context
import com.yapp.android2.domain.key.ACCESS_TOKEN_KEY
import com.yapp.android2.domain.key.AUTHORIZED
import com.yapp.android2.domain.key.SHARED_PREFERENCE_KEY
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class HeaderInterceptor @Inject constructor(
    @ApplicationContext context: Context
) : Interceptor {

    private val preference by lazy {
        context.getSharedPreferences(SHARED_PREFERENCE_KEY, Context.MODE_PRIVATE)
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken = preference.getString(ACCESS_TOKEN_KEY, "").orEmpty()
        val authorization = "Bearer $accessToken"
        val header = chain.request().newBuilder().addHeader(AUTHORIZED, authorization).build()

        return chain.proceed(header)
    }
}
