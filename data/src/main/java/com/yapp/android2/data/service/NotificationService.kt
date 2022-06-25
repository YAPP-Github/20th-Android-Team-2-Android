package com.yapp.android2.data.service

import com.yapp.android2.domain.entity.NotificationRequest
import com.yapp.android2.domain.entity.NotificationResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface NotificationService: Service {

    @POST("/api/fcm-token")
    suspend fun postFCMToken(
        @Body request: NotificationRequest
    ): NotificationResponse

}
