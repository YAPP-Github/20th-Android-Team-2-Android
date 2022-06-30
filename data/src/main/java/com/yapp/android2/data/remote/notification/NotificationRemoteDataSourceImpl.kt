package com.yapp.android2.data.remote.notification

import com.yapp.android2.data.service.NotificationService
import javax.inject.Inject

class NotificationRemoteDataSourceImpl @Inject constructor(
    private val notificationService: NotificationService
): NotificationRemoteDataSource {

}
