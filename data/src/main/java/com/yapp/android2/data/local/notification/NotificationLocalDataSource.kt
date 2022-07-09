package com.yapp.android2.data.local.notification

interface NotificationLocalDataSource {
    fun saveLastNotificationTime(time: String)
    fun getLastNotificationTime(): String
}
