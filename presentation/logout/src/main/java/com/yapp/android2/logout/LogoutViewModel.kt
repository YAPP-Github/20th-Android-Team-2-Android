package com.yapp.android2.logout

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.best.friends.core.BaseViewModel
import com.yapp.android2.domain.repository.logout.LogoutRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogoutViewModel @Inject constructor(
    private val logoutRepository: LogoutRepository
) : BaseViewModel() {

    private val _finish = MutableLiveData<Unit>()

    val finish: LiveData<Unit>
        get() = _finish

    fun logout() {
        viewModelScope.launch {
            val result = logoutRepository.logout()

            if (result.isSuccess) {
                _finish.value = Unit
            } else {
                sendErrorMessage("알 수 없는 에러입니다.")
            }
        }
    }
}
