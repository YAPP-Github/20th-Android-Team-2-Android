package com.yapp.android2.data.repository

import com.yapp.android2.data.remote.login.LoginRemoteDataSource
import com.yapp.android2.domain.entity.Login
import com.yapp.android2.domain.entity.LoginResponse
import com.yapp.android2.domain.repository.login.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val loginRemoteDataSource: LoginRemoteDataSource
): LoginRepository {

    override suspend fun login(loginType: Login.Type): LoginResponse {
        return loginRemoteDataSource.login(loginType)
    }
}
