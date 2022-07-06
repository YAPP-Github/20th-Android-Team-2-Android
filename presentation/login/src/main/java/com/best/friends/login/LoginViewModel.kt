package com.best.friends.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.best.friends.core.BaseViewModel
import com.yapp.android2.domain.entity.NotificationRequest
import com.yapp.android2.domain.repository.login.LoginRepository
import com.yapp.android2.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val loginRepository: LoginRepository
) : BaseViewModel() {
    private val _fcmToken = MutableLiveData<String>()
    val fcmToken: LiveData<String> = _fcmToken

    private val _isSuccess = MutableLiveData(false)
    val isSuccess: LiveData<Boolean> = _isSuccess

    private val _isRegisterUser = MutableLiveData(false)
    val isRegisterUser: LiveData<Boolean> = _isRegisterUser

    fun addKakaoUser(email: String, nickName: String, providerId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                loginUseCase(
                    email = email,
                    nickName = nickName,
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
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                loginUseCase.postFCMToken(NotificationRequest(requireNotNull(fcmToken.value)))
            }.onSuccess {
                _isSuccess.postValue(true)
            }.onFailure {
                Timber.tag("--- LoginViewModel - FCM Token error").e("$it")
            }
        }
    }
}
