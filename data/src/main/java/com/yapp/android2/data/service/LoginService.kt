package com.yapp.android2.data.service

import com.yapp.android2.domain.entity.Login
import com.yapp.android2.domain.entity.base.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface LoginService : Service {

    @GET("api/oauth/{socialLoginType}")
    suspend fun login(@Path("socialLoginType") loginType: Login.Type = Login.Type.KAKAO): ApiResponse<String>

}
