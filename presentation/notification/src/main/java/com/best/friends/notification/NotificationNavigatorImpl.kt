package com.best.friends.notification

import android.content.Context
import android.content.Intent
import com.best.friends.navigator.NotificationNavigator
import javax.inject.Inject

class NotificationNavigatorImpl @Inject constructor() : NotificationNavigator {

    override fun intent(context: Context): Intent {
        return Intent(context, NotificationActivity::class.java)
    }
}
