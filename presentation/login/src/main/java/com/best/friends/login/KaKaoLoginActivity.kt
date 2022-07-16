package com.best.friends.login

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.best.friends.core.BaseActivity
import com.best.friends.core.extensions.showToast
import com.best.friends.login.databinding.ActivityKakaoLoginBinding
import com.best.friends.navigator.HomeNavigator
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.yapp.android2.deeplink.DeepLinkPrefixSpec
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@DeepLinkPrefixSpec(
    value = [
        "login"
    ]
)
@AndroidEntryPoint
class KaKaoLoginActivity : BaseActivity<ActivityKakaoLoginBinding>(R.layout.activity_kakao_login) {

    private val viewModel by viewModels<LoginViewModel>()

    @Inject
    lateinit var homeNavigator: HomeNavigator

    @Inject
    lateinit var firebaseMessaging: FirebaseMessaging

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initFirebase()
        setKakaoLogin()
        isRegisterUserObserver()
        isSuccessObserver()
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

    private fun setKakaoLogin() {
        val context = this
        binding.clKakaoLogin.setOnClickListener {
            lifecycleScope.launch {
                kotlin.runCatching {
                    UserApiClient.loginWithKakaoOrThrow(context)
                }.onSuccess {
                    registerUser()
                }.onFailure {
                    if (it is ClientError && it.reason == ClientErrorCause.Cancelled) {
                        Timber.d("사용자가 명시적으로 카카오 로그인 취소")
                    } else {
                        showToast("로그인에 실패하였습니다")
                        Timber.e("$it")
                    }
                }
            }
        }
    }

    private fun registerUser() {
        UserApiClient.instance.me { user, _ ->
            if (user != null) {
                viewModel.addKakaoUser(
                    user.kakaoAccount?.email ?: "",
                    user.kakaoAccount?.profile?.nickname ?: "",
                    user.id ?: 0
                )
            }
        }
    }

    private fun isRegisterUserObserver(){
        viewModel.isRegisterUser.observe(this){
            if (it) {
                viewModel.addFCMToken()
            }
        }
    }

    private fun isSuccessObserver() {
        viewModel.isSuccess.observe(this) {
            if (it) {
                // 메인 화면으로 이동
                val intent = homeNavigator.intent(this)
                startActivity(intent)
                finish()
            }
        }
    }
}
