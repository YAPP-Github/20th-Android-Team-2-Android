package com.yapp.android2.data.repository

import com.yapp.android2.data.local.login.LoginLocalDataSource
import com.yapp.android2.domain.repository.setting.SettingRepository
import javax.inject.Inject

class SettingRepositoryImpl @Inject constructor(
    private val localDataSource: LoginLocalDataSource
) : SettingRepository {
    override fun getUserOrThrow(): SettingRepository.Settings.Success {
        val user = localDataSource.getUser()

        return if(user.email.isNullOrEmpty()) {
            throw IllegalArgumentException("email is empty")
        } else {
            SettingRepository.Settings.Success(user.email.orEmpty(), user.createAt)
        }
    }

}
