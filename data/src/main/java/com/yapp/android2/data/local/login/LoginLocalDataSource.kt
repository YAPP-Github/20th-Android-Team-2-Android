package com.yapp.android2.data.local.login

import com.yapp.android2.data.local.LocalDataSource
import com.yapp.android2.domain.entity.User

interface LoginLocalDataSource: LocalDataSource {
    fun saveAccessToken(token: String)
    fun getAccessToken(): String
    fun saveKakaoAccessToken(kakaoToken: String)
    fun getKakaoAccessToken(): String
    fun saveRefreshToken(refreshToken: String)
    fun getRefreshToken(): String
    fun saveUser(user: User)
    fun getUser(): User
}
