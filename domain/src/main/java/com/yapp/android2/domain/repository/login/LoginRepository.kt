package com.yapp.android2.domain.repository.login

import com.yapp.android2.domain.entity.*
import com.yapp.android2.domain.repository.Repository

interface LoginRepository : Repository {
    suspend fun postLogin(request: LoginRequest): LoginResponse
    suspend fun postFCMToken(request: NotificationRequest): NotificationResponse
    fun saveAccessToken(token: String)
    fun getAccessToken(): String
    fun saveKakaoAccessToken(kakaoToken: String)
    fun getKakaoAccessToken(): String
    fun saveRefreshToken(refreshToken: String)
    fun getRefreshToken(): String
    fun saveUser(user: User)
    fun getUser(): User
}
