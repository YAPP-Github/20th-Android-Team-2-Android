package com.yapp.android2.domain.repository.setting

interface SettingRepository {

    sealed class Settings {
        object Init : Settings()
        data class Success(val email: String, val createAt: String?): Settings()
        object Error : Settings()
    }

    fun getUserOrThrow(): Settings.Success
}
