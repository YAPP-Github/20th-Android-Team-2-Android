package com.yapp.android2.settings

import android.content.Context
import android.content.Intent
import com.best.friends.navigator.SettingNavigator
import javax.inject.Inject

class SettingsNavigatorImpl @Inject constructor() : SettingNavigator {
    override fun intent(context: Context): Intent {
        return Intent(context, SettingsActivity::class.java)
    }
}
