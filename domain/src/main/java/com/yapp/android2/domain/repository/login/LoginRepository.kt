package com.yapp.android2.domain.repository.login

import com.yapp.android2.domain.entity.Login
import com.yapp.android2.domain.entity.base.ApiResponse
import com.yapp.android2.domain.repository.Repository
import com.yapp.android2.domain.usecase.LoginType

interface LoginRepository : Repository {
    suspend fun login(loginType: LoginType): ApiResponse<Login>
}
