package com.yapp.android2.data.service.interceptor

import android.content.Context
import android.content.Intent
import androidx.core.content.edit
import com.google.gson.Gson
import com.yapp.android2.data.service.Service.Companion.BASE_URL
import com.yapp.android2.domain.entity.RenewalResponse
import com.yapp.android2.domain.key.*
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class HeaderInterceptor @Inject constructor(
    @ApplicationContext private val context: Context,
    private val gson: Gson
) : Interceptor {

    private val preference by lazy {
        context.getSharedPreferences(SHARED_PREFERENCE_KEY, Context.MODE_PRIVATE)
    }

    private var accessToken: String = ""
        get() = preference.getString(ACCESS_TOKEN_KEY, "").orEmpty()
        set(token) {
            preference.edit(true) { putString(ACCESS_TOKEN_KEY, token) }
            field = token
        }

    private fun authorization(token: String): String = "Bearer $token"

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder().addHeader(AUTHORIZED, authorization(accessToken)).build()
        val response = chain.proceed(request)
        when (response.code) {
            401 -> {
                val refreshToken = preference.getString(REFRESH_TOKEN_KEY, "").orEmpty()

                for (i in 1..REPEAT_NUM) {
                    val renewalTokenRequest = chain.request().newBuilder().get().url("${BASE_URL}/api/token")
                        .addHeader(AUTHORIZED, authorization(refreshToken)).build()
                    val renewalTokenResponse = chain.proceed(renewalTokenRequest)

                    if (renewalTokenResponse.isSuccessful) {
                        val newAccessTokenData = gson.fromJson(
                            renewalTokenResponse.body?.string(),
                            RenewalResponse::class.java
                        )

                        accessToken = newAccessTokenData.data.accessToken.toString()
                        val newRequest = chain.request().newBuilder().addHeader(AUTHORIZED, authorization(accessToken)).build()
                        return chain.proceed(newRequest)
                    }
                }
                /** 리프레시 토큰 만료
                 * 1. 토큰 삭제
                 * 2. 로그인 화면으로 이동 */
                removeTokens() // 1. 토큰 삭제
            }
        }
        return response
    }

    private fun removeTokens(){
        preference.edit(true) { remove(ACCESS_TOKEN_KEY) }
        preference.edit(true) { remove(REFRESH_TOKEN_KEY) }
    }

    companion object {
        private const val REPEAT_NUM = 3
    }
}
