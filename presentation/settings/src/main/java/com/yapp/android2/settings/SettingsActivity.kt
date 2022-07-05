package com.yapp.android2.settings

import android.os.Bundle
import com.best.friends.core.BaseActivity
import com.best.friends.core.setOnSingleClickListener
import com.best.friends.navigator.LogoutNavigator
import com.yapp.android2.settings.databinding.ActivitySettingsBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsActivity : BaseActivity<ActivitySettingsBinding>(R.layout.activity_settings) {

    @Inject
    lateinit var logoutNavigator: LogoutNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        overridePendingTransition(R.anim.activity_in_transition, R.anim.activity_stay_transition)

        binding.tvLogout.setOnSingleClickListener {
            logoutNavigator.intent(this)
        }
    }



    override fun onDestroy() {
        overridePendingTransition(R.anim.activity_out_transition, R.anim.activity_stay_transition)
        super.onDestroy()
    }
}
