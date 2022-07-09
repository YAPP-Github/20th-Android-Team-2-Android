package com.yapp.android2.logout

import com.best.friends.navigator.LogoutNavigator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
internal abstract class LogoutModule {

    @Binds
    abstract fun bindLogoutNavigator(navi: LogoutNavigatorImpl): LogoutNavigator

}
