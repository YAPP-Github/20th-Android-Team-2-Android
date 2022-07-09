package com.yapp.android2.domain.usecase

import com.yapp.android2.domain.repository.login.LoginRepository
import javax.inject.Inject

class PostFCMTokenUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {

    suspend operator fun invoke(fcmToken: String) {
        return loginRepository.postFCMToken(fcmToken)
    }

}
