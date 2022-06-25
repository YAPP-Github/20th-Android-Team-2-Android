package com.yapp.android2.data.repository

import com.yapp.android2.data.remote.version.AppVersionCheckDataSource
import com.yapp.android2.domain.entity.Version
import com.yapp.android2.domain.repository.version.AppVersionRepository
import javax.inject.Inject

class AppVersionRepositoryImpl @Inject constructor(
    private val appVersionCheckDataSource: AppVersionCheckDataSource
) : AppVersionRepository {
    override suspend fun fetchPlayStore(): Version {
        return appVersionCheckDataSource.fetchPlayStore()
    }
}
