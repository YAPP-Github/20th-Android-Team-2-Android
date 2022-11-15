package com.best.friends.home.register

import androidx.lifecycle.viewModelScope
import com.best.friends.core.BaseViewModel
import com.best.friends.core.extensions.Empty
import com.yapp.android2.domain.repository.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class SavingItemAddViewModel @Inject constructor(
    private val productsRepository: ProductsRepository
) : BaseViewModel() {

    private val _state = MutableStateFlow(State())
    val state: StateFlow<State>
        get() = _state

    private val _action = MutableSharedFlow<Action>()
    val action: SharedFlow<Action>
        get() = _action

    fun setContentText(text: String) {
        _state.value = _state.value.copy(
            content = MutableStateFlow(text)
        )
    }

    fun addSavingItem() {
        viewModelScope.launch {
            kotlin.runCatching {
                productsRepository.postProducts(
                    name = state.value.content.value.trim(),
                    freqType = state.value.freqType,
                    freqInterval = state.value.freqInterval,
                    startYmd = state.value.formattedStartDate,
                    endYmd = state.value.formattedEndDate,
                )
            }.onSuccess {
                _action.emit(Action.ItemAdded)
            }.onFailure { throwable ->
                sendErrorMessage(throwable.message)
            }
        }
    }

    data class State(
        val isInitialized: Boolean = false,
        val content: MutableStateFlow<String> = MutableStateFlow(String.Empty),
        val startDate: LocalDate = LocalDate.now(),
        val endDate: LocalDate = LocalDate.now(),
        val checkSavingDayOfWeekList: List<Boolean> = listOf(false, false, false, false, false, false, false),
    ) {
        val formattedStartDate: String
            get() {
                return DateTimeFormatter.ofPattern("yyyyMMdd").format(startDate)
            }

        val formattedEndDate: String
            get() {
                return DateTimeFormatter.ofPattern("yyyyMMdd").format(endDate)
            }

        val freqInterval: String
            get() {
                return checkSavingDayOfWeekList.asString()
            }

        private val availSavingDayOfWeekList: List<Boolean>
            get() {
                if(startDate.plusDays(ONE_WEEK_DIFF).isAfter(endDate)) {
                    return listOf(true, true, true, true, true, true, true)
                }
                else {
                    val preDayOfWeek =
                        if (startDate.dayOfWeek.value < endDate.dayOfWeek.value) startDate.dayOfWeek.value
                        else endDate.dayOfWeek.value
                    val postDayOfWeek = if (startDate.dayOfWeek.value > endDate.dayOfWeek.value) startDate.dayOfWeek.value
                        else endDate.dayOfWeek.value

                    val availSavingDayOfWeek: MutableList<Boolean> = mutableListOf(false, false, false, false, false, false, false)
                    for (dayOfWeek in preDayOfWeek..postDayOfWeek) {
                        if (dayOfWeek == DayOfWeek.SUNDAY.value) availSavingDayOfWeek[SUNDAY] = true
                        else availSavingDayOfWeek[dayOfWeek - 1] = true
                    }
                    return availSavingDayOfWeek.toList()
                }
            }

        // 1(once, 시작일과 마지막날짜가 같은 경우), 2(Daily, 매일 하는 경우), 3(Weekly, 날짜 지정하는 경우)
        val freqType: String
            get() {
                return if (formattedStartDate == formattedEndDate) ONCE
                else if (availSavingDayOfWeekList == checkSavingDayOfWeekList) DAILY
                else WEEKLY
            }
    }

    sealed class Action {
        object ItemAdded : Action()
    }

    companion object {
        const val ONE_WEEK_DIFF: Long = 5
        const val SUNDAY = 6
        const val ONCE = "1"
        const val DAILY = "2"
        const val WEEKLY = "3"
    }
}

fun List<Boolean>.asString(): String {
    val savingWeekData = ""
    for (index in this.indices) {
        if(this[index]) {
            savingWeekData.plus((index+1).toString())
        }
    }
    return savingWeekData
}
