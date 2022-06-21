package com.best.friends.splash

import android.os.Bundle
import androidx.activity.viewModels
import com.best.friends.core.BaseActivity
import com.best.friends.navigator.LoginNavigator
import com.best.friends.navigator.SecondsNavigator
import com.best.friends.splash.databinding.ActivitySplashBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {

    private val viewModel by viewModels<SplashViewModel>()
    private val activityScope = CoroutineScope(Dispatchers.Main)

    @Inject
    lateinit var loginNavigator: LoginNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityScope.launch {
            delay(2000)

            if (viewModel.isAlreadyUser()){
                //메인 화면으로 이동
            }
            else {
                startActivity(loginNavigator.intent(this@SplashActivity))
            }
            finish()
        }
    }

    override fun onPause() {
        activityScope.cancel()
        super.onPause()
    }
}
