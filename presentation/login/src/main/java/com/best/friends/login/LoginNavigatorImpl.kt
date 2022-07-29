package com.best.friends.login

import android.content.Context
import android.content.Intent
import com.best.friends.navigator.LoginNavigator
import com.yapp.android2.domain.repository.RemoteConfigRepository
import javax.inject.Inject

class LoginNavigatorImpl @Inject constructor(
    private val remoteConfigRepository: RemoteConfigRepository
) : LoginNavigator {

    override fun intent(context: Context): Intent {
        return  if (remoteConfigRepository.isLoginOnlySNS()) {
            // 구글로그인, 카카오로그인만 있는 화면
            Intent(context, LoginActivity::class.java)
        } else {
            // 자체 로그인 입력란이 있는 화면
            Intent(context, LoginForAppReviewActivity::class.java)
        }
    }
}
