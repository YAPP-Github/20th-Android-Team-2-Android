package com.yapp.android2.settings

import androidx.lifecycle.viewModelScope
import com.best.friends.core.BaseViewModel
import com.yapp.android2.domain.repository.setting.SettingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    settingUserData: SettingsUserDataParseFactory
) : BaseViewModel() {

    val user = settingUserData.getUser()
        .stateIn(
            scope = viewModelScope,
            initialValue = SettingRepository.Settings.Init,
            started = SharingStarted.WhileSubscribed(5000L)
        )

    fun logout() {
        //TODO
    }

    fun onUserWithDraw() {
        //TODO
    }
}
