package com.best.friends.home.register

import androidx.lifecycle.viewModelScope
import com.best.friends.core.BaseViewModel
import com.best.friends.core.ui.Empty
import com.yapp.android2.domain.repository.ProductsRepository
import com.yapp.android2.domain.repository.login.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavingItemAddViewModel @Inject constructor(
    private val productsRepository: ProductsRepository,
    private val loginRepository: LoginRepository
) : BaseViewModel() {

    val content = MutableStateFlow(String.Empty)
    val price = MutableStateFlow(String.Empty)

    private val _action = MutableSharedFlow<Action>()
    val action: SharedFlow<Action>
        get() = _action

    fun setText(text: String) {
        price.value = text
    }

    fun addSavingItem() {
        viewModelScope.launch {
            kotlin.runCatching {
                val user = loginRepository.getUser()
                productsRepository.postProducts(
                    userId = user.userId,
                    name = content.value,
                    price = price.value
                        .replace(",", "")
                        .replace("원", "")
                )
            }.onSuccess {
                _action.emit(Action.ItemAdded)
            }.onFailure { throwable ->
                sendErrorMessage(throwable.message)
            }
        }
    }

    sealed class Action {
        object ItemAdded : Action()
    }
}
