package com.yapp.android2.domain.repository.login

import com.yapp.android2.domain.entity.LoginRequest
import com.yapp.android2.domain.entity.LoginResponse
import com.yapp.android2.domain.entity.User
import com.yapp.android2.domain.repository.Repository

interface LoginRepository : Repository {
    suspend fun postLogin(request: LoginRequest): LoginResponse
    fun saveAccessToken(token: String)
    fun getAccessToken(): String
    fun saveKakaoAccessToken(kakaoToken: String)
    fun getKakaoAccessToken(): String
    fun saveUser(user: User)
    fun getUser(): User
}
