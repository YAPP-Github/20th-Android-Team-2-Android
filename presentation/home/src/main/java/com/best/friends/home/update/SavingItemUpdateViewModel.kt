package com.best.friends.home.update

import androidx.lifecycle.viewModelScope
import com.best.friends.core.BaseViewModel
import com.yapp.android2.domain.entity.Product
import com.yapp.android2.domain.repository.ProductsRepository
import com.yapp.android2.domain.repository.login.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavingItemUpdateViewModel @Inject constructor(
    private val params: Params,
    private val productsRepository: ProductsRepository,
    private val loginRepository: LoginRepository
) : BaseViewModel() {

    val paramsFlow = MutableStateFlow(params)
    val content = MutableStateFlow(params.product.name.orEmpty())
    val price = MutableStateFlow(params.product.wonPrice)

    private val _action = MutableSharedFlow<Action>()
    val action: SharedFlow<Action>
        get() = _action

    fun setContentText(text: String) {
        content.value = text
    }

    fun setPriceText(text: String) {
        price.value = text
    }

    fun onUpdateClick() {
        viewModelScope.launch {
            kotlin.runCatching {
                val name = content.value.trim()
                val price = price.value
                    .replace(",", "")
                    .replace("원", "")
                if (params.product.name != name || params.product.price != price) {
                    val user = loginRepository.getUser()
                    productsRepository.updateProducts(
                        productId = params.product.productId,
                        userId = user.userId,
                        name = name,
                        price = price
                    )
                } else {
                    return@launch _action.emit(Action.Finish)
                }
            }.onSuccess {
                _action.emit(Action.Update)
            }.onFailure { throwable ->
                sendErrorMessage(throwable.message)
            }
        }
    }

    fun onDeleteClick() {
        viewModelScope.launch {
            kotlin.runCatching {
                productsRepository.deleteProduct(productId = params.product.productId)
            }.onSuccess {
                _action.emit(Action.Delete)
            }.onFailure { throwable ->
                sendErrorMessage(throwable.message)
            }
        }
    }

    data class Params(val product: Product)

    sealed class Action {
        object Finish : Action()
        object Update : Action()
        object Delete : Action()
    }
}
