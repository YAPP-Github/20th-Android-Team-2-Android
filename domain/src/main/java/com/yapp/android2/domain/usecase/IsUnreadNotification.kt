package com.yapp.android2.domain.usecase

import com.yapp.android2.domain.repository.Notification.NotificationRepository
import java.text.SimpleDateFormat
import javax.inject.Inject

/** 읽지 않은 알림이 있는지 판단
 * 읽지 않은 알림이 있는 경우 true
 * 읽지 않은 알림이 없는 경우 false */
class IsUnreadNotification @Inject constructor(
    private val notificationRepository: NotificationRepository
) {
    suspend operator fun invoke(): Boolean {
        kotlin.runCatching {
            notificationRepository.getRecentCreatedNotification()
        }.onSuccess {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", java.util.Locale.getDefault())
            val remoteLastNotification = it.createAt
            val localLastNotification = notificationRepository.getLastNotificationTime()
            return when {
                remoteLastNotification == null -> {
                    false
                }
                localLastNotification == "" -> {
                    true
                }
                else -> {
                    val remoteLastNotificationFormat = dateFormat.parse(remoteLastNotification)
                    val localLastNotificationFormat = dateFormat.parse(localLastNotification)
                    remoteLastNotificationFormat.after(localLastNotificationFormat)
                }
            }
        }.onFailure { throw it }

        return false
    }
}
