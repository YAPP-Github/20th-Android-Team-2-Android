package com.yapp.android2.data.remote.login

import com.yapp.android2.data.service.LoginService
import com.yapp.android2.domain.entity.Login
import com.yapp.android2.domain.entity.LoginResponse
import javax.inject.Inject

class LoginRemoteDataSourceImpl @Inject constructor(
    private val loginService: LoginService
) : LoginRemoteDataSource {

    override suspend fun login(loginType: Login.Type): LoginResponse {
        return loginService.login(loginType)
    }

}
