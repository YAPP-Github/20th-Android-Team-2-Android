package com.best.friends.splash

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.best.friends.core.BaseActivity
import com.best.friends.navigator.HomeNavigator
import com.best.friends.navigator.LoginNavigator
import com.best.friends.splash.databinding.ActivitySplashBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {

    private val viewModel by viewModels<SplashViewModel>()

    @Inject
    lateinit var loginNavigator: LoginNavigator

    @Inject
    lateinit var homeNavigator: HomeNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        applySplashScreen()
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            delay(2000)

            if (viewModel.isAlreadyUser()) {
                //메인 화면으로 이동
                startActivity(homeNavigator.intent(this@SplashActivity))
            } else {
                startActivity(loginNavigator.intent(this@SplashActivity))
            }
            finish()
        }
    }

    private fun applySplashScreen() {
        val screen = installSplashScreen()
        screen.setKeepOnScreenCondition { true }
    }
}
