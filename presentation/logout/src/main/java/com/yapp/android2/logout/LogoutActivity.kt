package com.yapp.android2.logout

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.best.friends.core.BaseActivity
import com.best.friends.core.setOnSingleClickListener
import com.best.friends.navigator.LoginNavigator
import com.yapp.android2.logout.databinding.ActivityLogoutBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LogoutActivity : BaseActivity<ActivityLogoutBinding>(R.layout.activity_logout) {

    private val viewModel by viewModels<LogoutViewModel>()

    @Inject
    lateinit var loginNavigator: LoginNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.tvLogout.setOnSingleClickListener { viewModel.logout() }

        binding.ivBack.setOnSingleClickListener { finish() }

        viewModel.finish.observe(this) {
            startActivity(loginNavigator.intent(this).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
            finishAffinity()
        }
    }
}
