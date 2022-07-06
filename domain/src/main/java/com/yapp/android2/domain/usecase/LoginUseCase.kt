package com.yapp.android2.domain.usecase

import com.yapp.android2.domain.entity.LoginRequest
import com.yapp.android2.domain.entity.LoginResponse
import com.yapp.android2.domain.entity.NotificationRequest
import com.yapp.android2.domain.entity.User
import com.yapp.android2.domain.repository.login.LoginRepository
import java.time.LocalDateTime
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {

    suspend fun login(request: LoginRequest): LoginResponse {
        return loginRepository.postLogin(request)
    }

    suspend fun postFCMToken(request: NotificationRequest) {
        return loginRepository.postFCMToken(request)
    }

    fun saveAccessToken(token: String) {
        loginRepository.saveAccessToken(token)
    }

    fun saveKakaoAccessToken(token: String) {
        loginRepository.saveKakaoAccessToken(token)
    }

    fun saveRefreshToken(token: String){
        loginRepository.saveRefreshToken(token)
    }

    fun getAccessToken(): String = loginRepository.getAccessToken()

    fun getKakaoAccessToken(): String = loginRepository.getKakaoAccessToken()

    fun getRefreshToken(): String = loginRepository.getRefreshToken()

    fun saveUser(userId: Long, nickname: String, email: String, createAt: String) {
        val user = User(userId, nickname, email, createAt)
        loginRepository.saveUser(user)
    }
}
