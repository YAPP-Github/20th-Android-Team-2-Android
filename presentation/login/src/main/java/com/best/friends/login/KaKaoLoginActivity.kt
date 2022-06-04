package com.best.friends.login

import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.registerForActivityResult
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.best.friends.core.BaseActivity
import com.best.friends.login.databinding.ActivityKakaoLoginBinding
import com.best.friends.login.util.loginWithKakao
import com.kakao.sdk.common.KakaoSdk
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

        initKakaoSdk()
        setKakaoLogin()
    }

    private fun initKakaoSdk() = KakaoSdk.init(this, getString(R.string.kakao_native_app_key))

    private fun setKakaoLogin(){
        val context = this
        binding.clKakaoLogin.setOnClickListener {
            lifecycleScope.launch {
                try {
                    val oAuthToken = UserApiClient.loginWithKakao(context, callback)
                } catch (error: Throwable) {
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        Timber.d("사용자가 명시적으로 카카오 로그인 취소")
                    } else {
                        Timber.e("인증 에러 : $error")
                    }
                }
            }
        }
    }

    private val callback:() -> Unit = {
        UserApiClient.instance.me { user, _ ->
            if (user != null && user.kakaoAccount?.emailNeedsAgreement == true){
                Timber.d("-- 카카오 로그인 프로필 닉네임 -- : ${user.kakaoAccount?.profile?.nickname}")
                Timber.d("-- 카카오 로그인 프로필 이메일 -- : ${user.kakaoAccount?.email}")
            }
        }
    }
}