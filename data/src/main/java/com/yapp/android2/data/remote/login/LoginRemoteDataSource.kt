package com.yapp.android2.data.remote.login

import com.yapp.android2.data.remote.RemoteDataSource
import com.yapp.android2.domain.entity.Login
import com.yapp.android2.domain.entity.base.ApiResponse

interface LoginRemoteDataSource : RemoteDataSource {

    suspend fun login(loginType: Login.Type): ApiResponse<String>

}
