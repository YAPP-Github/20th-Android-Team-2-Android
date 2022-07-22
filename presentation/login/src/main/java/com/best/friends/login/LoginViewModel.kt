package com.best.friends.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.viewModelScope
import com.best.friends.core.BaseViewModel
import com.yapp.android2.domain.usecase.LoginUseCase
import com.yapp.android2.domain.usecase.PostFCMTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val postFCMTokenUseCase: PostFCMTokenUseCase
) : BaseViewModel() {
    private val _fcmToken = MutableLiveData<String>()
    val fcmToken: LiveData<String> = _fcmToken

    private val _isSuccess = MutableLiveData(false)
    val isSuccess: LiveData<Boolean> = _isSuccess.distinctUntilChanged()

    private val _isRegisterUser = MutableLiveData(false)
    val isRegisterUser: LiveData<Boolean> = _isRegisterUser

    fun addUser(email: String, nickName: String, provider: String, providerId: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                loginUseCase(
                    email = email,
                    nickName = nickName,
                    provider = provider,
                    providerId = providerId
                )
            }.onSuccess {
                _isRegisterUser.postValue(true)

            }.onFailure { throwable ->
                Timber.e("--- LoginViewModel - Login error: ${throwable.message}")
            }
        }
    }

    fun setFCMToken(token: String) {
        _fcmToken.value = token
    }

    fun addFCMToken() {
        viewModelScope.launch {
            kotlin.runCatching {
                postFCMTokenUseCase(requireNotNull(fcmToken.value))
            }.onSuccess {
                _isSuccess.postValue(true)
            }.onFailure {
                Timber.tag("--- LoginViewModel - FCM Token error").e("$it")
            }
        }
    }
}
