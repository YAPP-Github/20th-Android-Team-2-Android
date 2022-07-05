package com.yapp.android2.drawwith

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.best.friends.core.BaseViewModel
import com.yapp.android2.domain.key.EMAIL
import com.yapp.android2.domain.repository.withdraw.WithDrawRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WithDrawViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val withDrawRepository: WithDrawRepository
) : BaseViewModel() {

    val email: String? = savedStateHandle.get<String?>(EMAIL)

    private val _finish = MutableLiveData<Unit>()
    val finish: LiveData<Unit>
        get() = _finish

    fun withDraw() {
        viewModelScope.launch {
            val result = withDrawRepository.withDraw()

            if (result.isSuccess) {
                _finish.value = Unit
            } else {
                sendErrorMessage("알 수 없는 에러가 발생했어요")
            }
        }
    }
}
