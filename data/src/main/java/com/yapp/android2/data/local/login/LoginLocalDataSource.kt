package com.yapp.android2.data.local.login

import com.yapp.android2.data.local.LocalDataSource

interface LoginLocalDataSource: LocalDataSource {
    fun saveAccessToken(token: String)
    fun getAccessToken(): String
    fun saveKakaoAccessToken(kakaoToken: String)
    fun getKakaoAccessToken(): String
}
