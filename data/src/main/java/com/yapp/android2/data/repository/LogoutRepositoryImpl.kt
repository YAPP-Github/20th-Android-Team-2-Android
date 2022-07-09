package com.yapp.android2.data.repository

import com.yapp.android2.data.local.logout.LogoutLocalDataSource
import com.yapp.android2.data.remote.logout.LogoutRemoteDataSource
import com.yapp.android2.domain.repository.logout.LogoutRepository
import javax.inject.Inject

class LogoutRepositoryImpl @Inject constructor(
    private val logoutRemoteDataSource: LogoutRemoteDataSource,
    private val logoutLocalDataSourceImpl: LogoutLocalDataSource
) : LogoutRepository {

    override suspend fun logout() = runCatching {
        logoutRemoteDataSource.logout()
    }.onSuccess {
        clearRefreshToken()
        clearAccessToken()
        clearUser()
    }.onFailure { it.printStackTrace() }

    private fun clearUser() {
        logoutLocalDataSourceImpl.clearUser()
    }

    private fun clearAccessToken() {
        logoutLocalDataSourceImpl.clearAccessToken()
    }

    private fun clearRefreshToken() {
        logoutLocalDataSourceImpl.clearRefreshToken()
    }
}