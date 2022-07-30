package com.yapp.android2.data.remote.login

import com.yapp.android2.data.remote.request.LoginForAppReviewRequest
import com.yapp.android2.data.remote.request.PostLoginRequest
import com.yapp.android2.data.service.LoginService
import com.yapp.android2.domain.entity.FCMToken
import com.yapp.android2.domain.entity.User
import javax.inject.Inject

class LoginRemoteDataSourceImpl @Inject constructor(
    private val loginService: LoginService
) : LoginRemoteDataSource {

    override suspend fun postLogin(request: PostLoginRequest): User {
        return loginService.postLogin(request).data ?: User.EMPTY
    }

    override suspend fun postFCMToken(request: FCMToken) {
        return loginService.postFCMToken(request)
    }

    override suspend fun loginForAppReview(request: LoginForAppReviewRequest): User {
        return loginService.loginForAppReview(request).data ?: User.EMPTY
    }
}
