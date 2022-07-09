package com.yapp.android2.data.local.logout

interface LogoutLocalDataSource {
    fun clearUser()
    fun clearAccessToken()
    fun clearRefreshToken()
}
