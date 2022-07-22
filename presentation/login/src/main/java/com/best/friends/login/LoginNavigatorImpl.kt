package com.best.friends.login

import android.content.Context
import android.content.Intent
import com.best.friends.navigator.LoginNavigator
import javax.inject.Inject

class LoginNavigatorImpl @Inject constructor() : LoginNavigator {

    override fun intent(context: Context): Intent {
        return Intent(context, LoginActivity::class.java)
    }
}
