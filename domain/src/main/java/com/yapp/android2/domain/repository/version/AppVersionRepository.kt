package com.yapp.android2.domain.repository.version

import com.yapp.android2.domain.entity.Version

interface AppVersionRepository {
    suspend fun fetchPlayStore(): Version
}
