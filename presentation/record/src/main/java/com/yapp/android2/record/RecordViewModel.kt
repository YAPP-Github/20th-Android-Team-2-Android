package com.yapp.android2.record

import androidx.lifecycle.viewModelScope
import com.best.friends.core.BaseViewModel
import com.yapp.android2.domain.repository.record.Item
import com.yapp.android2.domain.usecase.GetRecordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class RecordViewModel @Inject constructor(
    private val getRecordUseCase: GetRecordUseCase
) : BaseViewModel() {

    private val _items: MutableStateFlow<List<Item>> = MutableStateFlow(emptyList())
    val items: StateFlow<List<Item>>
        get() = _items

    var recordJob: Job? = null

    /**
     * @param date format yyyyMM
     */
    fun fetchRecords() {
        recordJob?.cancel()

        recordJob = viewModelScope.launch {
            try {
                val response = getRecordUseCase.execute(Unit)

                _items.value = response
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
        }
    }


    override fun onCleared() {
        recordJob?.cancel()
        super.onCleared()
    }
}
