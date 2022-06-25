package com.yapp.android2.settings

import android.os.Bundle
import com.best.friends.core.BaseActivity
import com.yapp.android2.settings.databinding.ActivitySettingsBinding


class SettingsActivity : BaseActivity<ActivitySettingsBinding>(R.layout.activity_settings) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        overridePendingTransition(R.anim.activity_in_transition, R.anim.activity_stay_transition)
    }



    override fun onDestroy() {
        overridePendingTransition(R.anim.activity_out_transition, R.anim.activity_stay_transition)
        super.onDestroy()
    }
}
