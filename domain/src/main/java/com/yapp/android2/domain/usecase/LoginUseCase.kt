package com.yapp.android2.domain.usecase

import com.yapp.android2.domain.entity.User
import com.yapp.android2.domain.repository.login.LoginRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {

    suspend operator fun invoke(
        email: String,
        nickName: String,
        providerId: Long
    ): Result<User> {
        return kotlin.runCatching {
            loginRepository.postLogin(
                email = email,
                nickName = nickName,
                providerId = providerId
            )
        }.onSuccess {
            loginRepository.saveUser(it)
        }.onFailure {
            throw it
        }
    }
}
