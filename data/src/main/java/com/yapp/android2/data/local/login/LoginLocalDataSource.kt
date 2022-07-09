package com.yapp.android2.data.local.login

import com.yapp.android2.data.local.LocalDataSource
import com.yapp.android2.domain.entity.User

interface LoginLocalDataSource: LocalDataSource {
    fun saveUser(user: User)
    fun getUser(): User
}
