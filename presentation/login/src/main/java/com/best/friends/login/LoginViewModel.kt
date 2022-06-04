package com.best.friends.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.best.friends.core.BaseViewModel
import com.yapp.android2.domain.entity.Login
import com.yapp.android2.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : BaseViewModel() {

    private val _redirectUrl = MutableLiveData<String>()
    val redirectUrl: LiveData<String> = _redirectUrl
    private val _isAvailUrl = MutableLiveData(false)
    val isAvailUrl: LiveData<Boolean> = _isAvailUrl

    fun login(snsType: Login.Type = Login.Type.KAKAO) {
        viewModelScope.launch {
            try {
                val response = loginUseCase.execute(LoginUseCase.Params(snsType))

                _redirectUrl.postValue(response.data)
                _isAvailUrl.postValue(true)
            }
            catch (e: Exception){
                Timber.d(e)
            }
        }
    }
}