package com.best.friends.home

import androidx.lifecycle.viewModelScope
import com.best.friends.core.BaseViewModel
import com.yapp.android2.domain.entity.Product
import com.yapp.android2.domain.repository.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val productsRepository: ProductsRepository
) : BaseViewModel() {

    private val _state = MutableStateFlow(State())
    val state: StateFlow<State>
        get() = _state

    init {
        viewModelScope.launch {
            kotlin.runCatching {
                productsRepository.getProductsToday()

            }.onSuccess { products ->
                _state.value = _state.value.copy(products = products)
            }
        }
    }

    data class State(
        val day: ZonedDateTime = ZonedDateTime.now(ZoneId.systemDefault()),
        val products: List<Product> = emptyList()
    ) {
        companion object {
            val Default = State()
        }
    }
}
