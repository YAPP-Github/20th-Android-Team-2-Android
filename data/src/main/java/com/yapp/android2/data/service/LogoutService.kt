package com.yapp.android2.data.service

import retrofit2.http.GET

interface LogoutService : Service {

    @GET("/api/user/logout")
    suspend fun logout()
}
