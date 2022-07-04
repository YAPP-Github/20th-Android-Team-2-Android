package com.best.friends.splash

import com.best.friends.core.BaseViewModel
import com.yapp.android2.domain.usecase.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase
) : BaseViewModel() {

    fun isAlreadyUser() = getUserUseCase().accessToken.isNotEmpty()
}
