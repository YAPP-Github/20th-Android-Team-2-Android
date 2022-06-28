package com.best.friends.notification

import com.best.friends.core.BaseViewModel
import com.yapp.android2.domain.usecase.NotificationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val notificationUseCase: NotificationUseCase
) : BaseViewModel() {

}
