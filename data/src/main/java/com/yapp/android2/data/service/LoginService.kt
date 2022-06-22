package com.yapp.android2.data.service

import com.yapp.android2.domain.entity.LoginRequest
import com.yapp.android2.domain.entity.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService : Service {

    @POST("/api/oauth/sign-in")
    suspend fun postLogin(
        @Body request: LoginRequest
    ): LoginResponse

}
