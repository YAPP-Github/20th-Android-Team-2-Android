package com.yapp.android2.data.repository

import com.yapp.android2.data.local.login.LoginLocalDataSource
import com.yapp.android2.data.remote.login.LoginRemoteDataSource
import com.yapp.android2.domain.entity.Login
import com.yapp.android2.domain.entity.LoginRequest
import com.yapp.android2.domain.entity.LoginResponse
import com.yapp.android2.domain.entity.User
import com.yapp.android2.domain.repository.login.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val loginRemoteDataSource: LoginRemoteDataSource,
    private val loginLocalDataSource: LoginLocalDataSource
): LoginRepository {

    override suspend fun postLogin(request: LoginRequest): LoginResponse {
        return loginRemoteDataSource.postLogin(request)
    }

    override fun saveAccessToken(token: String) {
        loginLocalDataSource.saveAccessToken(token)
    }

    override fun getAccessToken(): String = loginLocalDataSource.getAccessToken()

    override fun saveKakaoAccessToken(kakaoToken: String) {
        loginLocalDataSource.saveKakaoAccessToken(kakaoToken)
    }

    override fun getKakaoAccessToken(): String = loginLocalDataSource.getKakaoAccessToken()

    override fun saveUser(user: User) {
        loginLocalDataSource.saveUser(user)
    }

    override fun getUser(): User {
        return loginLocalDataSource.getUser()
    }
}
