package com.yapp.android2.data.local.login

import com.yapp.android2.data.local.BestFriendSharedPreferenceProviderImpl
import com.yapp.android2.domain.key.ACCESS_TOKEN_KEY
import com.yapp.android2.domain.key.KAKAO_ACCESS_TOKEN_KEY
import javax.inject.Inject

class LoginLocalDataSourceImpl @Inject constructor(
    private val bestFriendSharedPreferenceImpl: BestFriendSharedPreferenceProviderImpl
): LoginLocalDataSource {
    override fun saveAccessToken(token: String) {
        bestFriendSharedPreferenceImpl.putString(ACCESS_TOKEN_KEY, token)
    }

    override fun getAccessToken(): String {
        return bestFriendSharedPreferenceImpl.getString(ACCESS_TOKEN_KEY)
    }


    override fun saveKakaoAccessToken(kakaoToken: String) {
        bestFriendSharedPreferenceImpl.putString(KAKAO_ACCESS_TOKEN_KEY, kakaoToken)
    }

    override fun getKakaoAccessToken(): String {
        return bestFriendSharedPreferenceImpl.getString(KAKAO_ACCESS_TOKEN_KEY)
    }
}
