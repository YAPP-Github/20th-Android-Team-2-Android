package com.yapp.android2.data.service

import com.yapp.android2.domain.entity.Notification
import com.yapp.android2.domain.entity.RecentNotification
import com.yapp.android2.domain.entity.base.ApiResponse
import retrofit2.http.GET

interface NotificationService: Service {

    @GET("/api/alarm")
    suspend fun getNotification(): ApiResponse<List<Notification>>

    @GET("/api/alarm/recent-created")
    suspend fun getRecentCreatedNotification(): ApiResponse<RecentNotification>
}
