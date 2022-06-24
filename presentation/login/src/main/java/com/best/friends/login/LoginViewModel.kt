package com.best.friends.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.best.friends.core.BaseViewModel
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
    private val _accessToken = MutableLiveData<String>()
    val accessToken: LiveData<String> = _accessToken
    private val _kakaoAccessToken = MutableLiveData<String>()
    val kakaoAccessToken: LiveData<String> = _kakaoAccessToken
    private val _isSuccess = MutableLiveData(false)
    val isSuccess: LiveData<Boolean> = _isSuccess

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
                loginUseCase.saveAccessToken(requireNotNull(it.data.accessToken))
                _accessToken.postValue(requireNotNull(it.data.accessToken))
                _isSuccess.postValue(true)
            }.onFailure {
                Timber.e("$it")
            }
        }
    }

    fun setKakaoAccessToken(token: String){
        _kakaoAccessToken.value = token
        loginUseCase.saveKakaoAccessToken(token)
    }
}
