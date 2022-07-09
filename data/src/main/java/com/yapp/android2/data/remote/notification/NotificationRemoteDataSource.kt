package com.yapp.android2.data.remote.notification

import com.yapp.android2.data.remote.RemoteDataSource
import com.yapp.android2.domain.entity.Notification
import com.yapp.android2.domain.entity.RecentNotification

interface NotificationRemoteDataSource: RemoteDataSource {
    suspend fun getNotification(): List<Notification>
    suspend fun getRecentCreatedNotification(): RecentNotification
}
