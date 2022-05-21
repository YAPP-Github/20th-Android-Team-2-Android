package com.best.friends.seconds

import com.best.friends.navigator.SecondsNavigator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class SecondsModule {

    @Binds
    abstract fun bindSecondsNavigator(navigator: SecondsNavigatorImpl): SecondsNavigator
}
