package com.yapp.android2.data.remote.notification

import com.yapp.android2.data.service.NotificationService
import com.yapp.android2.domain.entity.Notification
import com.yapp.android2.domain.entity.RecentNotification
import javax.inject.Inject

class NotificationRemoteDataSourceImpl @Inject constructor(
    private val notificationService: NotificationService
): NotificationRemoteDataSource {

    override suspend fun getNotification(): List<Notification> {
        return notificationService.getNotification().data ?: emptyList()
    }

    override suspend fun getRecentCreatedNotification(): RecentNotification {
        return notificationService.getRecentCreatedNotification().data ?: RecentNotification.EMPTY
    }

}
