package com.yapp.android2.data.local.notification

import com.yapp.android2.data.local.BestFriendSharedPreferenceProviderImpl
import com.yapp.android2.domain.key.LAST_NOTIFICATION_TIME_KEY
import javax.inject.Inject

class NotificationLocalDataSourceImpl @Inject constructor(
    private val preference: BestFriendSharedPreferenceProviderImpl
): NotificationLocalDataSource {
    override fun saveLastNotificationTime(time: String) {
        preference.putString(LAST_NOTIFICATION_TIME_KEY, time)
    }

    override fun getLastNotificationTime(): String {
        return preference.getString(LAST_NOTIFICATION_TIME_KEY)
    }
}
