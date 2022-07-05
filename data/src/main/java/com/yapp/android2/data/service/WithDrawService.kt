package com.yapp.android2.data.service

import retrofit2.http.GET

interface WithDrawService : Service {

    @GET("/api/user/withdrawal")
    suspend fun withDraw()
}
