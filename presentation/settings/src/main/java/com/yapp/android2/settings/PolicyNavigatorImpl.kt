package com.yapp.android2.settings

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.best.friends.navigator.PolicyNavigator
import javax.inject.Inject

class PolicyNavigatorImpl @Inject constructor(): PolicyNavigator {
    override fun intent(context: Context): Intent {
        return Intent(
            Intent.ACTION_VIEW,
            Uri.parse("https://bit.ly/3yn5yL4")
        )
    }
}
