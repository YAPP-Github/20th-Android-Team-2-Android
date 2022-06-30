package com.yapp.android2.data.remote.login

import com.yapp.android2.data.service.LoginService
import com.yapp.android2.domain.entity.LoginRequest
import com.yapp.android2.domain.entity.LoginResponse
import com.yapp.android2.domain.entity.NotificationRequest
import com.yapp.android2.domain.entity.NotificationResponse
import javax.inject.Inject

class LoginRemoteDataSourceImpl @Inject constructor(
    private val loginService: LoginService
) : LoginRemoteDataSource {

    override suspend fun postLogin(request: LoginRequest): LoginResponse {
        return loginService.postLogin(request)
    }

    override suspend fun postFCMToken(request: NotificationRequest): NotificationResponse {
        return loginService.postFCMToken(request)
    }
}
