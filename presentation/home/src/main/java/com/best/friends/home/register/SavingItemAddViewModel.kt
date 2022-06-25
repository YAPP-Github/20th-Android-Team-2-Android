package com.best.friends.home.register

import com.best.friends.core.BaseViewModel
import com.best.friends.core.ui.Empty
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class SavingItemAddViewModel @Inject constructor() : BaseViewModel() {

    val content = MutableStateFlow(String.Empty)
    val price = MutableStateFlow(String.Empty)

    fun setText(text: String) {
        price.value = text
    }
}
