package com.yapp.android2.data.local.logout

import com.yapp.android2.data.local.SharedPreferenceProvider
import com.yapp.android2.domain.entity.User
import com.yapp.android2.domain.key.ACCESS_TOKEN_KEY
import com.yapp.android2.domain.key.REFRESH_TOKEN_KEY
import javax.inject.Inject

class LogoutLocalDataSourceImpl @Inject constructor(
    private val preferenceProvider: SharedPreferenceProvider
) : LogoutLocalDataSource {
    
    override fun clearUser() {
        preferenceProvider.user = User.EMPTY
    }

    override fun clearAccessToken() {
        preferenceProvider.putString(ACCESS_TOKEN_KEY, "")
    }

    override fun clearRefreshToken() {
        preferenceProvider.putString(REFRESH_TOKEN_KEY, "")
    }
}
