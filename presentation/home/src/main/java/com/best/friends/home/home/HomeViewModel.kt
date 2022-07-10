package com.best.friends.home.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.best.friends.core.BaseViewModel
import com.yapp.android2.domain.entity.Product
import com.yapp.android2.domain.repository.ProductsRepository
import com.yapp.android2.domain.repository.login.LoginRepository
import com.yapp.android2.domain.repository.record.RecordRepository
import com.yapp.android2.domain.usecase.IsUnreadNotification
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.ZoneId
import java.time.ZonedDateTime
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val productsRepository: ProductsRepository,
    private val recordRepository: RecordRepository,
    private val loginRepository: LoginRepository,
    private val isUnreadNotification: IsUnreadNotification
) : BaseViewModel() {

    private val _state = MutableStateFlow(State())
    val state: StateFlow<State>
        get() = _state.asStateFlow()

    private val _action = MutableSharedFlow<Action>()
    val action: SharedFlow<Action>
        get() = _action.asSharedFlow()

    private val _isNotification = MutableLiveData(false)
    val isNotification: LiveData<Boolean> = _isNotification

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

    fun getProductsSelectDay(zonedDateTime: ZonedDateTime) {
        viewModelScope.launch {
            _loading.value = true
            kotlin.runCatching {
                productsRepository.getProductsByZonedDateTime(zonedDateTime)
            }.onSuccess { products ->
                _state.value = _state.value.copy(
                    day = zonedDateTime,
                    products = products
                )
            }.onFailure { throwable ->
                _state.value = _state.value.copy(
                    day = zonedDateTime,
                    products = emptyList()
                )
                sendErrorMessage(throwable.message)
            }

            _loading.value = false
        }
    }

    fun checkSavingItem(product: Product) {
        viewModelScope.launch {
            kotlin.runCatching {
                val user = loginRepository.getUser()
                recordRepository.updateRecords(product, user)
            }.onSuccess {
                val mutableList = _state.value.products.toMutableList()
                val index = mutableList.indexOfFirst { it.productId == product.productId }
                mutableList[index] = product.copy(checked = !product.checked)
                _state.value = _state.value.copy(products = mutableList)
            }.onFailure { throwable ->
                Timber.e("--- HomeViewModel error: ${throwable.message}")
                sendErrorMessage(throwable.message)
            }
        }
    }

    fun onCalendarClick() {
        viewModelScope.launch {
            val currentDay = _state.value.day
            _action.emit(Action.CalendarClick(currentDay))
        }
    }

    fun onPrevArrowClick() {
        val currentDay = _state.value.day
        val prevDay = currentDay.minusDays(1L)
        getProductsSelectDay(prevDay)
    }

    fun onNextArrowClick() {
        val currentDay = _state.value.day
        val nextDay = currentDay.plusDays(1L)
        getProductsSelectDay(nextDay)
    }

    fun setUnreadNotification() {
        viewModelScope.launch {
            kotlin.runCatching {
                isUnreadNotification()
            }.onSuccess {
                _isNotification.postValue(it)
            }.onFailure { throwable ->
                Timber.e("--- HomeViewModel error: ${throwable.message}")
                sendErrorMessage(throwable.message)
            }
        }

    }

    data class State(
        val isInitialized: Boolean = false,
        val day: ZonedDateTime = ZonedDateTime.now(ZoneId.systemDefault()),
        val products: List<Product> = emptyList()
    ) {

        val priceSum: Int
            get() = products
                .filter { it.checked && it.price.isNotBlank() }
                .sumOf { it.price.toInt() }

        val isPastDate: Boolean
            get() {
                val now = ZonedDateTime.now()
                val todayMidnight = ZonedDateTime.of(
                    now.year,
                    now.monthValue,
                    now.dayOfMonth, 0, 0, 0, 0, ZoneId.systemDefault()
                )
                return day.isBefore(todayMidnight)
            }
    }

    sealed class Action {
        data class CalendarClick(val currentDay: ZonedDateTime) : Action()
    }
}
