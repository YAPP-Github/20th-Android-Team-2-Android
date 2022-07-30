package com.yapp.android2.data.service

import com.yapp.android2.data.remote.request.LoginForAppReviewRequest
import com.yapp.android2.data.remote.request.PostLoginRequest
import com.yapp.android2.domain.entity.FCMToken
import com.yapp.android2.domain.entity.User
import com.yapp.android2.domain.entity.base.ApiResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService : Service {

    @POST("/api/oauth/sign-in")
    suspend fun postLogin(
        @Body request: PostLoginRequest
    ): ApiResponse<User>

    @POST("/api/fcm-token")
    suspend fun postFCMToken(
        @Body request: FCMToken
    )

    @POST("api/user/signin")
    suspend fun loginForAppReview(
        @Body request: LoginForAppReviewRequest
    ): ApiResponse<User>
}
