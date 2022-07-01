package com.yapp.android2.domain.repository.Notification

import com.yapp.android2.domain.entity.Notification

interface NotificationRepository {
    suspend fun getNotification(): List<Notification>
}
