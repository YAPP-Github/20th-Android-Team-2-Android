package com.yapp.android2.data.remote.version

import com.yapp.android2.domain.entity.Version

interface AppVersionCheckDataSource {
    suspend fun fetchPlayStore(): Version
}
