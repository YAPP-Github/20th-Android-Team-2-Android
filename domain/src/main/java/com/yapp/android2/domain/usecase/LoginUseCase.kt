package com.yapp.android2.domain.usecase

import com.yapp.android2.domain.entity.*
import com.yapp.android2.domain.repository.login.LoginRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {

    suspend fun login(request: LoginRequest): LoginResponse {
        return loginRepository.postLogin(request)
    }

    suspend fun postFCMToken(request: NotificationRequest): NotificationResponse {
        return loginRepository.postFCMToken(request)
    }

    fun saveAccessToken(token: String) {
        loginRepository.saveAccessToken(token)
    }

    fun saveKakaoAccessToken(token: String) {
        loginRepository.saveKakaoAccessToken(token)
    }

    fun getAccessToken(): String = loginRepository.getAccessToken()

    fun getKakaoAccessToken(): String = loginRepository.getKakaoAccessToken()

    fun saveUser(userId: Long, nickname: String) {
        val user = User(userId, nickname)
        loginRepository.saveUser(user)
    }
}
