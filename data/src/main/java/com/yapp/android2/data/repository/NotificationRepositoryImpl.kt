package com.yapp.android2.data.repository

import com.yapp.android2.data.remote.notification.NotificationRemoteDataSource
import com.yapp.android2.domain.entity.Notification
import com.yapp.android2.domain.repository.Notification.NotificationRepository
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val notificationRemoteDataSource: NotificationRemoteDataSource
): NotificationRepository {

    override suspend fun getNotification(): List<Notification> {
        return notificationRemoteDataSource.getNotification()
    }

}
