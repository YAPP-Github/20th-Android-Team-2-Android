package com.yapp.android2.data.remote.notification

import com.yapp.android2.data.remote.RemoteDataSource
import com.yapp.android2.domain.entity.Notification

interface NotificationRemoteDataSource: RemoteDataSource {
    suspend fun getNotification(): List<Notification>
}
