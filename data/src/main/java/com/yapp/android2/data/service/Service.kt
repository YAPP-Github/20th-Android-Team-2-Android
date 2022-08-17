package com.yapp.android2.data.service

import com.yapp.android2.data.extensions.createInterceptors
import okhttp3.Interceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface Service {
    companion object {
        const val BASE_URL = "http://dev.jeol-chin.com"
        const val VERSION = "1.1"

        internal fun retroBuilder(vararg interceptor: Interceptor): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(createInterceptors(interceptor.toList()))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}
