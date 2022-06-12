package com.yapp.android2.data.service

import com.yapp.android2.domain.entity.Login
import com.yapp.android2.domain.entity.LoginRequest
import com.yapp.android2.domain.entity.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface LoginService : Service {

    @POST("api/oauth/{socialLoginType}")
    suspend fun postLogin(
        @Body request: LoginRequest,
        @Path("socialLoginType") loginType: Login.Type = Login.Type.KAKAO
    ): LoginResponse

}
