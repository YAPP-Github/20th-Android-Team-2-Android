package com.yapp.android2.data.remote.login

import com.yapp.android2.data.remote.RemoteDataSource
import com.yapp.android2.data.remote.request.PostLoginRequest
import com.yapp.android2.domain.entity.NotificationRequest
import com.yapp.android2.domain.entity.User

interface LoginRemoteDataSource : RemoteDataSource {

    suspend fun postLogin(request: PostLoginRequest): User

    suspend fun postFCMToken(request: NotificationRequest)
}
