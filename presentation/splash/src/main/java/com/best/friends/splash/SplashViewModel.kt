package com.best.friends.splash

import com.best.friends.core.BaseViewModel
import com.yapp.android2.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : BaseViewModel() {

    fun isAlreadyUser() = loginUseCase.getAccessToken().isNotEmpty()
}
