package com.yapp.android2.data.repository

import com.yapp.android2.data.local.logout.LogoutLocalDataSource
import com.yapp.android2.data.remote.withdraw.WithDrawRemoteDataSource
import com.yapp.android2.domain.repository.withdraw.WithDrawRepository
import javax.inject.Inject

class WithDrawRepositoryImpl @Inject constructor(
    private val logoutLocalDataSource: LogoutLocalDataSource,
    private val withDrawRemoteDataSource: WithDrawRemoteDataSource
) : WithDrawRepository {
    override suspend fun withDraw() = runCatching { withDrawRemoteDataSource.withDraw() }
        .onSuccess {
            clearKeys()
            logoutLocalDataSource.clearUser()
        }
        .onFailure { it.printStackTrace() }

    private fun clearKeys() {
        logoutLocalDataSource.clearAccessToken()
        logoutLocalDataSource.clearRefreshToken()
    }
}
