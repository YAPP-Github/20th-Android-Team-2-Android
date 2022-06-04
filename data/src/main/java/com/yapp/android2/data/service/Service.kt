package com.yapp.android2.data.service

import com.yapp.android2.data.service.convert.BestFriendResponseConvertFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface Service {
    companion object {
        private const val BASE_URL = "https://61.79.96.183:8443/"
        private const val HOST_NAME = "61.79.96.183"

        internal fun retroBuilder(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(getOkhttpClient())
                .addConverterFactory(BestFriendResponseConvertFactory.create())
                .build()
        }

        private fun getOkhttpClient() = OkHttpClient.Builder().apply {
            hostnameVerifier { hostname, _ ->
                hostname!!.contentEquals(HOST_NAME)
            }
        }.build()
    }
}
