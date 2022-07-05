package com.yapp.android2.settings

import android.content.Context
import android.content.Intent
import com.best.friends.navigator.LogoutNavigator
import com.yapp.android2.logout.LogoutActivity
import javax.inject.Inject

class LogoutNavigatorImpl @Inject constructor() : LogoutNavigator {


    override fun intent(context: Context): Intent {
        return Intent(context, LogoutActivity::class.java)
    }
}
