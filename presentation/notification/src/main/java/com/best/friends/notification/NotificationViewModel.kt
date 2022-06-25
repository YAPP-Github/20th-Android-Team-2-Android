package com.best.friends.notification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.best.friends.core.BaseViewModel
import com.yapp.android2.domain.entity.NotificationRequest
import com.yapp.android2.domain.usecase.NotificationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val notificationUseCase: NotificationUseCase
) : BaseViewModel() {

    private val _fcmToken = MutableLiveData<String>()
    val fcmToken: LiveData<String> = _fcmToken

    fun addFCMToken(tokenData: NotificationRequest){
        viewModelScope.launch {
            kotlin.runCatching {
                notificationUseCase.postFCMToken(tokenData)
            }.onSuccess {
                Timber.tag("FCM-Server-Connect").d("$it")
                _fcmToken.postValue(requireNotNull(tokenData.fcmToken))
            }.onFailure {
                Timber.tag("FCM-Server-Connect").e("$it")
            }
        }
    }
}
