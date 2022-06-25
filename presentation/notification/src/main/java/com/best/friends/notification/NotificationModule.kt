package com.best.friends.notification

import com.best.friends.navigator.NotificationNavigator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class NotificationModule {

    @Binds
    abstract fun bindNotificationNavigator(navigator: NotificationNavigatorImpl): NotificationNavigator
}
