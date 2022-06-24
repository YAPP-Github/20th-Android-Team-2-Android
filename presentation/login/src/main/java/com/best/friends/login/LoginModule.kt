package com.best.friends.login

import com.best.friends.navigator.LoginNavigator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class LoginModule {

    @Binds
    abstract fun bindLoginNavigator(navigator: LoginNavigatorImpl): LoginNavigator
}
