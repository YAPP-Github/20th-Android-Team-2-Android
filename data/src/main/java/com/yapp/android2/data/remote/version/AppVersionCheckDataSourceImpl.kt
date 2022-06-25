package com.yapp.android2.data.remote.version

import com.yapp.android2.domain.entity.Version
import com.yapp.android2.domain.repository.version.AppVersionRepository
import javax.inject.Inject

class AppVersionCheckDataSourceImpl @Inject constructor(
    private val repository: AppVersionRepository
) : AppVersionCheckDataSource {
    override suspend fun fetchPlayStore(): Version {
        return repository.fetchPlayStore()
    }
}
