package com.yapp.android2.data.service

import com.yapp.android2.domain.entity.Login
import com.yapp.android2.domain.entity.base.ApiResponse
import com.yapp.android2.domain.usecase.LoginType
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Path

interface LoginService : Service {

    @GET("api/oauth/{socialLoginType}")
    suspend fun login(@Path("socialLoginType") loginType: LoginType): ApiResponse<Login>

    companion object {
        fun create(): LoginService {
            return Service.retroBuilder()
                .create()
        }
    }
}
