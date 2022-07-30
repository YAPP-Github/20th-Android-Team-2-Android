package com.best.friends.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.viewModelScope
import com.best.friends.core.BaseViewModel
import com.best.friends.core.extensions.Empty
import com.yapp.android2.domain.repository.login.LoginRepository
import com.yapp.android2.domain.usecase.PostFCMTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginForAppReviewViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val postFCMTokenUseCase: PostFCMTokenUseCase
) : BaseViewModel() {

    val email = MutableStateFlow(String.Empty)
    val password = MutableStateFlow(String.Empty)

    private val _fcmToken = MutableLiveData<String>()
    val fcmToken: LiveData<String>
        get() = _fcmToken

    private val _isSuccess = MutableLiveData(false)
    val isSuccess: LiveData<Boolean>
        get() = _isSuccess.distinctUntilChanged()

    private val _isRegisterUser = MutableLiveData(false)
    val isRegisterUser: LiveData<Boolean>
        get() = _isRegisterUser

    fun login() {
        viewModelScope.launch {
            startLoading()
            kotlin.runCatching {
                loginRepository.loginForAppReview(email.value, password.value)
            }.onSuccess {
                _isRegisterUser.postValue(true)
            }.onFailure {

            }

            stopLoading()
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
