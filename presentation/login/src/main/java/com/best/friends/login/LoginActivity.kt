package com.best.friends.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.best.friends.core.BaseActivity
import com.best.friends.core.extensions.showToast
import com.best.friends.login.databinding.ActivityLoginBinding
import com.best.friends.navigator.HomeNavigator
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.messaging.FirebaseMessaging
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.yapp.android2.domain.entity.User
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {

    private val viewModel by viewModels<LoginViewModel>()
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var googleSignResultLauncher: ActivityResultLauncher<Intent>

    @Inject
    lateinit var homeNavigator: HomeNavigator

    @Inject
    lateinit var firebaseMessaging: FirebaseMessaging

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initFirebase()
        initGoogleLogin()
        startKakaoLogin()
        startGoogleLogin()
        setGoogleLogin()
        observe()
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

    private fun initGoogleLogin(){
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.google_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    private fun startKakaoLogin() {
        val context = this
        binding.clKakaoLogin.setOnClickListener {
            lifecycleScope.launch {
                kotlin.runCatching {
                    UserApiClient.loginWithKakaoOrThrow(context)
                }.onSuccess {

                    UserApiClient.instance.me { user, _ ->
                        if (user != null) {
                            viewModel.addUser(
                                user.kakaoAccount?.email ?: "",
                                user.kakaoAccount?.profile?.nickname ?: "",
                                User.Type.KAKAO.toString(),
                                user.id.toString()
                            )
                        }
                    }

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

    private fun startGoogleLogin(){
        binding.clGoogleLogin.setOnClickListener {
            val intent = googleSignInClient.signInIntent
            googleSignResultLauncher.launch(intent)
        }
    }

    private fun setGoogleLogin(){
        googleSignResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()){ result ->
            if (result.resultCode == RESULT_OK) {

                kotlin.runCatching{
                    val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                    task.getResult(ApiException::class.java)
                }.onSuccess {
                    viewModel.addUser(
                        it.email ?: "",
                        it.displayName ?: "",
                        User.Type.GOOGLE.toString(),
                        it.id ?: ""
                    )

                    Timber.i("구글 id ${it.id}")
                    Timber.i("구글 displayName ${it.displayName}")
                    Timber.i("구글 email ${it.email}")
                }.onFailure {
                    showToast("로그인에 실패하였습니다")
                    Timber.e("$it")
                }

            } else {
                showToast("로그인에 실패하였습니다")
                Timber.d("구글 ${result.resultCode.toString() + "+" + RESULT_OK.toString()}")
            }
        }
    }

    private fun observe(){
        viewModel.isRegisterUser.observe(this){
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
}
