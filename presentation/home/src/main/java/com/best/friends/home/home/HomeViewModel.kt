package com.best.friends.home.home

import androidx.lifecycle.viewModelScope
import com.best.friends.core.BaseViewModel
import com.yapp.android2.domain.entity.Product
import com.yapp.android2.domain.repository.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.ZoneId
import java.time.ZonedDateTime
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val productsRepository: ProductsRepository
) : BaseViewModel() {

    private val _state = MutableStateFlow(State())
    val state: StateFlow<State>
        get() = _state

    init {
        getProductsToday()
    }

    fun getProductsToday() {
        viewModelScope.launch {
            _loading.value = true
            kotlin.runCatching {
                productsRepository.getProductsToday()
            }.onSuccess { products ->
                _state.value = _state.value.copy(
                    isInitialized = true,
                    products = products
                )
            }.onFailure { throwable ->
                Timber.e("--- HomeViewModel error: ${throwable.message}")
                _state.value = _state.value.copy(isInitialized = true)
                sendErrorMessage(throwable.message)
            }

            _loading.value = false
        }
    }

    data class State(
        val isInitialized: Boolean = false,
        val day: ZonedDateTime = ZonedDateTime.now(ZoneId.systemDefault()),
        val products: List<Product> = emptyList()
    )
}
