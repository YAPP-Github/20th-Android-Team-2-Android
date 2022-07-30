package com.best.friends.login

import android.os.Bundle
import androidx.activity.viewModels
import com.best.friends.core.BaseActivity
import com.best.friends.login.databinding.ActivityLoginForAppReviewBinding
import com.best.friends.navigator.HomeNavigator
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class LoginForAppReviewActivity :
    BaseActivity<ActivityLoginForAppReviewBinding>(R.layout.activity_login_for_app_review) {

    private val viewModel by viewModels<LoginForAppReviewViewModel>()

    @Inject
    lateinit var homeNavigator: HomeNavigator

    @Inject
    lateinit var firebaseMessaging: FirebaseMessaging

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        observe()
        initFirebase()
    }

    private fun initView() {
        binding.viewModel = viewModel
    }

    private fun observe() {
        viewModel.isRegisterUser.observe(this) {
            if (it) {
                viewModel.addFCMToken()
            }
        }

        viewModel.isSuccess.observe(this) {
            if (it) {
                // 메인 화면으로 이동
                val intent = homeNavigator.intent(this)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun initFirebase() {
        firebaseMessaging.token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Timber.e(task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result
            viewModel.setFCMToken(token)
            Timber.i("FCM Token: $token")
        })
    }
}
