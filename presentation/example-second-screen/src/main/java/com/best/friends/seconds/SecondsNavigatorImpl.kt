package com.best.friends.seconds

import android.content.Context
import android.content.Intent
import com.best.friends.navigator.SecondsNavigator
import javax.inject.Inject

internal class SecondsNavigatorImpl @Inject constructor() : SecondsNavigator {

    override fun intent(context: Context): Intent {
        return Intent(context, SecondsActivity::class.java)
    }
}
