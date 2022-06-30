package com.yapp.android2.domain.usecase

import com.yapp.android2.domain.repository.Notification.NotificationRepository
import javax.inject.Inject

class NotificationUseCase @Inject constructor(
    private val notificationRepository: NotificationRepository
) {

}
