package com.best.friends.notification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.best.friends.core.BaseViewModel
import com.yapp.android2.domain.entity.Notification
import com.yapp.android2.domain.repository.Notification.NotificationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val notificationRepository: NotificationRepository
) : BaseViewModel() {

    private val _notificationList = MutableLiveData<List<Notification>>()
    val notificationList: LiveData<List<Notification>> = _notificationList

    init {
        getNotificationList()
    }

    private fun getNotificationList() {
        viewModelScope.launch {
            kotlin.runCatching {
                notificationRepository.getNotification()
            }.onSuccess {
                _notificationList.postValue(it)

                if(it.isNotEmpty()) {
                    saveLastNotificationTime(it[0].createAt ?: "0000-00-00T00:00:00")
                }
            }.onFailure { throwable ->
                Timber.e("--- NotificationViewModel error: ${throwable.message}")
                sendErrorMessage(throwable.message)
            }
        }
    }

    private fun saveLastNotificationTime(time: String) {
        notificationRepository.saveLastNotificationTime(time)
    }
}
