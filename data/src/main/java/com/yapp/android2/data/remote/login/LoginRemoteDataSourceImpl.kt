package com.yapp.android2.data.remote.login

import com.yapp.android2.data.service.LoginService
import com.yapp.android2.domain.entity.Login
import com.yapp.android2.domain.entity.base.ApiResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRemoteDataSourceImpl @Inject constructor(
    private val loginService: LoginService
) : LoginRemoteDataSource {

    override suspend fun login(loginType: Login.Type): ApiResponse<String> {
        return loginService.login(loginType)
    }

}
