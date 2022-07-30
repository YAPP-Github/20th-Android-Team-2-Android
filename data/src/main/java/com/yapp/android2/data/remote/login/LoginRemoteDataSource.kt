package com.yapp.android2.data.remote.login

import com.yapp.android2.data.remote.RemoteDataSource
import com.yapp.android2.data.remote.request.LoginForAppReviewRequest
import com.yapp.android2.data.remote.request.PostLoginRequest
import com.yapp.android2.domain.entity.FCMToken
import com.yapp.android2.domain.entity.User

interface LoginRemoteDataSource : RemoteDataSource {

    suspend fun postLogin(request: PostLoginRequest): User

    suspend fun postFCMToken(request: FCMToken)

    suspend fun loginForAppReview(request: LoginForAppReviewRequest): User
}
