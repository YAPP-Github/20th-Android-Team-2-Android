package com.best.friends.login

import android.os.Bundle
import com.best.friends.core.BaseActivity
import com.best.friends.login.databinding.ActivityLoginForAppReviewBinding
import com.best.friends.navigator.HomeNavigator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginForAppReviewActivity :
    BaseActivity<ActivityLoginForAppReviewBinding>(R.layout.activity_login_for_app_review) {

    @Inject
    lateinit var homeNavigator: HomeNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}
