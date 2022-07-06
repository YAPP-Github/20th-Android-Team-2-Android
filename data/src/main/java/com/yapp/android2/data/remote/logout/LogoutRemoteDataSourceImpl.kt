package com.yapp.android2.data.remote.logout

import com.yapp.android2.data.service.LogoutService
import javax.inject.Inject

class LogoutRemoteDataSourceImpl @Inject constructor(
    private val logoutService: LogoutService
): LogoutRemoteDataSource {

    override suspend fun logout() = logoutService.logout()

}
