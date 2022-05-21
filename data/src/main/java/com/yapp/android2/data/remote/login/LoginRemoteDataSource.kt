package com.yapp.android2.data.remote.login

import com.yapp.android2.data.remote.RemoteDataSource
import com.yapp.android2.domain.entity.Login
import com.yapp.android2.domain.entity.base.ApiResponse
import com.yapp.android2.domain.usecase.LoginType

interface LoginRemoteDataSource : RemoteDataSource {

    suspend fun login(loginType: LoginType): ApiResponse<Login>

}
