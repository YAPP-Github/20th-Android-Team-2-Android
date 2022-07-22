package com.yapp.android2.data.repository

import com.yapp.android2.data.local.login.LoginLocalDataSource
import com.yapp.android2.data.remote.login.LoginRemoteDataSource
import com.yapp.android2.data.remote.request.PostLoginRequest
import com.yapp.android2.domain.entity.FCMToken
import com.yapp.android2.domain.entity.User
import com.yapp.android2.domain.repository.login.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val loginRemoteDataSource: LoginRemoteDataSource,
    private val loginLocalDataSource: LoginLocalDataSource
): LoginRepository {

    override suspend fun postLogin(email: String, nickName: String, provider: String, providerId: String): User {
        val request = PostLoginRequest(
            email = email,
            nickName = nickName,
            provider = provider,
            providerId = providerId
        )
        return loginRemoteDataSource.postLogin(request)
    }

    override suspend fun postFCMToken(fcmToken: String) {
        val request = FCMToken(
            fcmToken = fcmToken
        )
        return loginRemoteDataSource.postFCMToken(request)
    }

    override fun saveUser(user: User) {
        loginLocalDataSource.saveUser(user)
    }

    override fun getUser(): User {
        return loginLocalDataSource.getUser()
    }
}
