package com.yapp.android2.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    private val _error = MutableSharedFlow<String>()
    val error: SharedFlow<String>
        get() = _error

    protected fun sendErrorMessage(throwable: Throwable) {
        sendErrorMessage(throwable.message.toString())
    }

    protected fun sendErrorMessage(message: String) {
        viewModelScope.launch {
            _error.emit(message)
        }
    }
}
