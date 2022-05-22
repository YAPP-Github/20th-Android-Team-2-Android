package com.yapp.android2.domain.repository.login

import com.yapp.android2.domain.entity.Login
import com.yapp.android2.domain.entity.base.ApiResponse
import com.yapp.android2.domain.repository.Repository

interface LoginRepository : Repository {
    suspend fun login(loginType: Login.Type): ApiResponse<String>
}
