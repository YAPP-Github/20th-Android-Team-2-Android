package com.yapp.android2.data.local.login

import com.yapp.android2.data.local.BestFriendSharedPreferenceProviderImpl
import com.yapp.android2.domain.entity.User
import com.yapp.android2.domain.key.ACCESS_TOKEN_KEY
import com.yapp.android2.domain.key.KAKAO_ACCESS_TOKEN_KEY
import com.yapp.android2.domain.key.REFRESH_TOKEN
import javax.inject.Inject

class LoginLocalDataSourceImpl @Inject constructor(
    private val preference: BestFriendSharedPreferenceProviderImpl
): LoginLocalDataSource {
    override fun saveAccessToken(token: String) {
        preference.putString(ACCESS_TOKEN_KEY, token)
    }

    override fun getAccessToken(): String {
        return preference.getString(ACCESS_TOKEN_KEY)
    }

    override fun saveKakaoAccessToken(kakaoToken: String) {
        preference.putString(KAKAO_ACCESS_TOKEN_KEY, kakaoToken)
    }

    override fun getKakaoAccessToken(): String {
        return preference.getString(KAKAO_ACCESS_TOKEN_KEY)
    }

    override fun saveRefreshToken(refreshToken: String) {
        preference.putString(REFRESH_TOKEN, refreshToken)
    }

    override fun getRefreshToken(): String {
        return preference.getString(REFRESH_TOKEN)
    }

    override fun saveUser(user: User) {
        preference.user = user
    }

    override fun getUser(): User {
        return preference.user
    }
}
