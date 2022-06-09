package com.best.friends.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.best.friends.core.BaseViewModel
import com.yapp.android2.domain.entity.Login
import com.yapp.android2.domain.entity.LoginRequest
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
    private var _accessToken = MutableLiveData<String>()
    var accessToken: LiveData<String> = _accessToken
    private var _kakaoAccessToken = MutableLiveData<String>()
    var kakaoAccessToken: LiveData<String> = _kakaoAccessToken

    fun setKakaoUser(email: String, nickName: String, providerId: Long){
        val user = LoginRequest(
            email = email,
            nickName = nickName,
            providerId = providerId
        )
        _user.value = user
    }

    fun addKakaoUser(userData: LoginRequest, loginType: Login.Type = Login.Type.KAKAO){
        viewModelScope.launch {
            kotlin.runCatching {
                loginUseCase.login(userData, loginType)
            }.onSuccess {
                _accessToken.postValue(requireNotNull(it.data.accessToken))
            }.onFailure {
                Timber.e("$it")
            }
        }
    }
}