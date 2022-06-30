package com.yapp.android2.data.remote.login

import com.yapp.android2.data.remote.RemoteDataSource
import com.yapp.android2.domain.entity.LoginRequest
import com.yapp.android2.domain.entity.LoginResponse
import com.yapp.android2.domain.entity.NotificationRequest
import com.yapp.android2.domain.entity.NotificationResponse

interface LoginRemoteDataSource : RemoteDataSource {

    suspend fun postLogin(request: LoginRequest): LoginResponse

    suspend fun postFCMToken(request: NotificationRequest): NotificationResponse
}
