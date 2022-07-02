package com.yapp.android2.settings

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.best.friends.navigator.PolicyNavigator

class PolicyNavigatorImpl : PolicyNavigator {
    override fun intent(context: Context): Intent {
        return Intent(
            Intent.ACTION_VIEW,
            Uri.parse("https://rustic-decade-83c.notion.site/cdfb1587f8854c7e86a2ee46ec471311")
        )
    }
}
