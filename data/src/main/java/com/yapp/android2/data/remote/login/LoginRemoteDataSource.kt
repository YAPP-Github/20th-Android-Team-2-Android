package com.yapp.android2.data.remote.login

import com.yapp.android2.data.remote.RemoteDataSource
import com.yapp.android2.domain.entity.Login
import com.yapp.android2.domain.entity.LoginRequest
import com.yapp.android2.domain.entity.LoginResponse

interface LoginRemoteDataSource : RemoteDataSource {

    suspend fun postLogin(request: LoginRequest, loginType: Login.Type): LoginResponse

}
