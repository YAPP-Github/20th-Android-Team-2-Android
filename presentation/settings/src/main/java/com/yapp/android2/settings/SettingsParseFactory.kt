package com.yapp.android2.settings

import com.yapp.android2.domain.repository.setting.SettingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SettingsUserDataParseFactory @Inject constructor(
    private val settingRepository: SettingRepository
) {

    fun getUser(): Flow<SettingRepository.Settings> = flow {
        val user = settingRepository.getUserOrThrow()

        emit(user)
    }.catch { SettingRepository.Settings.Error }

}
