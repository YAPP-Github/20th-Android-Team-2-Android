package com.yapp.android2.data.repository

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.yapp.android2.domain.repository.RemoteConfigRepository
import javax.inject.Inject

class RemoteConfigRepositoryImpl @Inject constructor(
    private val remoteConfig: FirebaseRemoteConfig
) : RemoteConfigRepository {

    override fun isLoginOnlySNS(): Boolean {
        return remoteConfig.getBoolean(KEY_LOGIN_ONLY_SNS)
    }

    companion object {
        private const val KEY_LOGIN_ONLY_SNS = "login_only_sns"
    }
}
