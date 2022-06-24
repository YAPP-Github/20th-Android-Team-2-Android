package com.best.friends.login

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.best.friends.core.BaseActivity
import com.best.friends.core.util.ToastUtil
import com.best.friends.login.databinding.ActivityKakaoLoginBinding
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class KaKaoLoginActivity : BaseActivity<ActivityKakaoLoginBinding>(R.layout.activity_kakao_login) {

    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setKakaoLogin()
        isSuccessObserver()
    }

    private fun setKakaoLogin() {
        val context = this
        binding.clKakaoLogin.setOnClickListener {
            lifecycleScope.launch {
                try {
                    val oAuthToken = UserApiClient.loginWithKakaoOrThrow(context)

                    viewModel.setKakaoAccessToken(oAuthToken.accessToken)
                    registerUser()

                } catch (error: Throwable) {
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        Timber.d("사용자가 명시적으로 카카오 로그인 취소")
                    } else {
                        ToastUtil.showTextToast(context, "로그인에 실패하였습니다")
                        Timber.e("$error")
                    }
                }
            }
        }
    }

    private fun registerUser() {
        UserApiClient.instance.me { user, _ ->
            if (user != null) {
                viewModel.setKakaoUser(
                    user.kakaoAccount?.email ?: "",
                    user.kakaoAccount?.profile?.nickname ?: "",
                    user.id ?: 0
                )
                viewModel.user.value?.let { viewModel.addKakaoUser(it) }
            }
        }
    }

    private fun isSuccessObserver() {
        viewModel.isSuccess.observe(this) {
            if (it) {
                // 메인 화면으로 이동
            }
        }
    }
}
