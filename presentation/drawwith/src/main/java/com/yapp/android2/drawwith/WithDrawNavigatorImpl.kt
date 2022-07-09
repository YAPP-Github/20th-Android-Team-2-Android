package com.yapp.android2.drawwith

import android.content.Context
import android.content.Intent
import com.best.friends.navigator.WithDrawNavigator
import javax.inject.Inject

class WithDrawNavigatorImpl @Inject constructor() : WithDrawNavigator {
    override fun intent(context: Context): Intent {
        return Intent(context, WithDrawActivity::class.java)
    }
}
