package com.yapp.android2.domain.usecase

import com.yapp.android2.domain.UseCase
import com.yapp.android2.domain.entity.base.ApiResponse
import com.yapp.android2.domain.repository.login.LoginRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) : UseCase<LoginType, Result<ApiResponse>> {
    override suspend fun execute(params: LoginType): Result<ApiResponse> {
        return loginRepository.login()
    }

}

enum class LoginType { KAKAO }
