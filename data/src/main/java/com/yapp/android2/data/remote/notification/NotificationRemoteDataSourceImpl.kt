package com.yapp.android2.data.remote.notification

import com.yapp.android2.data.service.NotificationService
import com.yapp.android2.domain.entity.NotificationRequest
import com.yapp.android2.domain.entity.NotificationResponse
import javax.inject.Inject

class NotificationRemoteDataSourceImpl @Inject constructor(
    private val notificationService: NotificationService
): NotificationRemoteDataSource {

    override suspend fun postFCMToken(request: NotificationRequest): NotificationResponse {
        return notificationService.postFCMToken(request)
    }

}
