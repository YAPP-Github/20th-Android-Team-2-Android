package com.yapp.android2.data.service

import com.yapp.android2.data.extensions.createInterceptors
import com.yapp.android2.data.service.convert.BestFriendResponseConvertFactory
import okhttp3.Interceptor
import retrofit2.Retrofit

interface Service {
    companion object {
        private const val BASE_URL = "http://61.79.96.183:8080/"

        internal fun retroBuilder(vararg interceptor: Interceptor): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(createInterceptors(interceptor.toList()))
                .addConverterFactory(BestFriendResponseConvertFactory.create())
                .build()
        }
    }
}
