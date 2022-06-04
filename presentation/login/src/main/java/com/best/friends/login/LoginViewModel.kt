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
    private val _nickName = MutableLiveData<String>()
    private val _email = MutableLiveData<String>()

    val nickname: LiveData<String> = _nickName
    val email: LiveData<String> = _email

    fun setNickName(nickName: String){
        _nickName.value = nickName
    }

    fun setEmail(email: String){
        _email.value = email
    }
}