package com.best.friends.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.viewModelScope
import com.best.friends.core.BaseViewModel
import com.yapp.android2.domain.entity.LoginRequest
import com.yapp.android2.domain.entity.NotificationRequest
import com.yapp.android2.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : BaseViewModel() {
    private val _user = MutableLiveData<LoginRequest>()
    val user: LiveData<LoginRequest> = _user

    private val _accessToken = MutableLiveData<String>()
    val accessToken: LiveData<String> = _accessToken

    private val _refreshToken = MutableLiveData<String>()
    val refreshToken: LiveData<String> = _refreshToken

    private val _kakaoAccessToken = MutableLiveData<String>()
    val kakaoAccessToken: LiveData<String> = _kakaoAccessToken

    private val _fcmToken = MutableLiveData<String>()
    val fcmToken: LiveData<String> = _fcmToken

    private val _isSuccess = MutableLiveData(false)
    val isSuccess: LiveData<Boolean> = _isSuccess.distinctUntilChanged()

    private val _isRegisterUser = MutableLiveData(false)
    val isRegisterUser: LiveData<Boolean> = _isRegisterUser

    fun setKakaoUser(email: String, nickName: String, providerId: Long){
        val user = LoginRequest(
            email = email,
            nickName = nickName,
            providerId = providerId
        )
        _user.value = user
    }

    fun addKakaoUser(userData: LoginRequest){
        viewModelScope.launch {
            kotlin.runCatching {
                loginUseCase.login(userData)
            }.onSuccess {
                Timber.i("access token : ${it.data.accessToken}")
                Timber.i("refresh token : ${it.data.refreshToken}")
                loginUseCase.saveAccessToken(requireNotNull(it.data.accessToken))
                loginUseCase.saveRefreshToken(requireNotNull(it.data.refreshToken))
                loginUseCase.saveUser(it.data.userId ?: 0, it.data.nickName.orEmpty(), it.data.email.orEmpty(), it.data.createAt.orEmpty())

                _accessToken.postValue(requireNotNull(it.data.accessToken))
                _refreshToken.postValue(requireNotNull(it.data.refreshToken))
                _isSuccess.postValue(true)
                _isRegisterUser.postValue(true)
                
            }.onFailure {
                Timber.e("$it")
            }
        }
    }

    fun setKakaoAccessToken(token: String){
        _kakaoAccessToken.value = token
        loginUseCase.saveKakaoAccessToken(token)
    }


    fun setFCMToken(token: String){
        _fcmToken.value = token
    }

    fun addFCMToken(){
        viewModelScope.launch {
            kotlin.runCatching {
                loginUseCase.postFCMToken(NotificationRequest(requireNotNull(fcmToken.value)))
            }.onSuccess {
                Timber.tag("FCM-Server-Connect").d("$it")
                _isSuccess.postValue(true)
            }.onFailure {
                Timber.tag("FCM-Server-Connect").e("$it")
            }
        }
    }
}
