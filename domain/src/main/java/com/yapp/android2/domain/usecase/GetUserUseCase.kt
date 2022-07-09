package com.yapp.android2.domain.usecase

import com.yapp.android2.domain.entity.User
import com.yapp.android2.domain.repository.login.LoginRepository
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {

    operator fun invoke(): User {
        return loginRepository.getUser()
    }

}
