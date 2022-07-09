package com.yapp.android2.data.repository

import com.yapp.android2.data.local.notification.NotificationLocalDataSource
import com.yapp.android2.data.remote.notification.NotificationRemoteDataSource
import com.yapp.android2.domain.entity.Notification
import com.yapp.android2.domain.entity.RecentNotification
import com.yapp.android2.domain.repository.Notification.NotificationRepository
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val notificationRemoteDataSource: NotificationRemoteDataSource,
    private val notificationLocalDataSource: NotificationLocalDataSource
): NotificationRepository {

    override suspend fun getNotification(): List<Notification> {
        return notificationRemoteDataSource.getNotification()
    }

    override suspend fun getRecentCreatedNotification(): RecentNotification {
        return notificationRemoteDataSource.getRecentCreatedNotification()
    }

    override fun saveLastNotificationTime(time: String) {
        notificationLocalDataSource.saveLastNotificationTime(time)
    }

    override fun getLastNotificationTime(): String {
        return notificationLocalDataSource.getLastNotificationTime()
    }

}
