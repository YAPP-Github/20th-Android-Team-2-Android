package com.best.friends.home.home

import android.content.Context
import android.content.Intent
import com.best.friends.home.MainActivity
import com.best.friends.navigator.HomeNavigator
import javax.inject.Inject

internal class HomeNavigatorImpl @Inject constructor() : HomeNavigator {

    override fun intent(context: Context): Intent {
        return Intent(context, MainActivity::class.java)
    }
}
