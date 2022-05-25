package com.best.friends.home

import androidx.lifecycle.viewModelScope
import com.best.friends.core.BaseViewModel
import com.yapp.android2.domain.entity.Login
import com.yapp.android2.domain.entity.base.ApiResponse
import com.yapp.android2.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : BaseViewModel() {


    //FIXME just temporary code
    fun login(snsType: Login.Type = Login.Type.KAKAO) {
        viewModelScope.launch {
            val response = loginUseCase.execute(LoginUseCase.Params(snsType))

            when(response) {
                is ApiResponse.Success -> {

                }

                is ApiResponse.Error -> {

                }
            }
        }
    }
}
