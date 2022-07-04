package com.yapp.android2.data.local.login

import com.yapp.android2.data.local.BestFriendSharedPreferenceProviderImpl
import com.yapp.android2.domain.entity.User
import javax.inject.Inject

class LoginLocalDataSourceImpl @Inject constructor(
    private val preference: BestFriendSharedPreferenceProviderImpl
): LoginLocalDataSource {

    override fun saveUser(user: User) {
        preference.user = user
    }

    override fun getUser(): User {
        return preference.user
    }
}
