package com.yapp.android2.record

import androidx.lifecycle.viewModelScope
import com.best.friends.core.BaseViewModel
import com.yapp.android2.domain.Record
import com.yapp.android2.domain.repository.record.Item
import com.yapp.android2.domain.usecase.GetRecordUseCase
import com.yapp.android2.domain.usecase.RecordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class RecordViewModel @Inject constructor(
    private val getRecordUseCase: RecordUseCase
) : BaseViewModel() {

    private val dummy = listOf(
        Item(
            Record(
                name = "아이스크림",
                price = 2000,
                productId = 1,
                recordYmd = Date(System.currentTimeMillis()),
                resolution = "안녕"
            ), 22
        ),
        Item(
            Record(
                name = "아이스크림",
                price = 2000,
                productId = 1,
                recordYmd = Date(System.currentTimeMillis()),
                resolution = "안녕"
            ), 10
        ),
        Item(
            Record(
                name = "아이스크림",
                price = 2000,
                productId = 1,
                recordYmd = Date(System.currentTimeMillis()),
                resolution = "안녕"
            ), 2
        ), Item(
            Record(
                name = "아이스크림",
                price = 2000,
                productId = 1,
                recordYmd = Date(System.currentTimeMillis()),
                resolution = "안녕"
            ), 4
        ), Item(
            Record(
                name = "아이스크림",
                price = 2000,
                productId = 1,
                recordYmd = Date(System.currentTimeMillis()),
                resolution = "안녕"
            ), 5
        )
    )

    private val _items: MutableStateFlow<List<Item>> = MutableStateFlow(dummy)
    val items: StateFlow<List<Item>>
        get() = _items

    var recordJob: Job? = null

    fun fetchRecords() {
        recordJob?.cancel()
        items.value.sumOf { it.record.price }

        recordJob = viewModelScope.launch {
            try {
                val response = getRecordUseCase.execute(RecordUseCase.Params(""))

                _items.value = response
            } catch (exception: Exception) {

            }

        }
    }


    override fun onCleared() {
        recordJob?.cancel()
        super.onCleared()
    }
}
