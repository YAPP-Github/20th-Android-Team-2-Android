package com.yapp.android2.data.service

import com.yapp.android2.domain.entity.LoginRequest
import com.yapp.android2.domain.entity.LoginResponse
import com.yapp.android2.domain.entity.NotificationRequest
import com.yapp.android2.domain.entity.base.ApiResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService : Service {

    @POST("/api/oauth/sign-in")
    suspend fun postLogin(
        @Body request: LoginRequest
    ): LoginResponse

    @POST("/api/fcm-token")
    suspend fun postFCMToken(
        @Body request: NotificationRequest
    )
}
