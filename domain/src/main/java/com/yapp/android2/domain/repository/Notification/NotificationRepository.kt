package com.yapp.android2.domain.repository.Notification

import com.yapp.android2.domain.entity.Notification
import com.yapp.android2.domain.entity.RecentNotification

interface NotificationRepository {
    suspend fun getNotification(): List<Notification>
    suspend fun getRecentCreatedNotification(): RecentNotification
    fun saveLastNotificationTime(time: String)
    fun getLastNotificationTime(): String
}
