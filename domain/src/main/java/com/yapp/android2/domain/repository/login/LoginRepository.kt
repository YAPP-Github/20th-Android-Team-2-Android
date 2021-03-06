package com.yapp.android2.domain.repository.login

import com.yapp.android2.domain.entity.User
import com.yapp.android2.domain.repository.Repository

interface LoginRepository : Repository {

    suspend fun postLogin(
        email: String,
        nickName: String,
        provider: String,
        providerId: String
    ): User

    suspend fun loginForAppReview(email: String, password: String)

    suspend fun postFCMToken(fcmToken: String)

    fun saveUser(user: User)

    fun getUser(): User
}
