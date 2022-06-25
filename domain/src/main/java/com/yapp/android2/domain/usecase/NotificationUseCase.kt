package com.yapp.android2.domain.usecase

import com.yapp.android2.domain.entity.NotificationRequest
import com.yapp.android2.domain.entity.NotificationResponse
import com.yapp.android2.domain.repository.Notification.NotificationRepository
import javax.inject.Inject

class NotificationUseCase @Inject constructor(
    private val notificationRepository: NotificationRepository
) {

    suspend fun postFCMToken(request: NotificationRequest): NotificationResponse {
        return notificationRepository.postFCMToken(request)
    }

}
