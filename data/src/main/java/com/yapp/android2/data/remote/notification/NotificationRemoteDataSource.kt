package com.yapp.android2.data.remote.notification

import com.yapp.android2.data.remote.RemoteDataSource
import com.yapp.android2.domain.entity.NotificationRequest
import com.yapp.android2.domain.entity.NotificationResponse

interface NotificationRemoteDataSource: RemoteDataSource {

    suspend fun postFCMToken(request: NotificationRequest): NotificationResponse
}
