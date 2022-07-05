package com.yapp.android2.drawwith

import com.best.friends.navigator.WithDrawNavigator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
internal abstract class WithDrawModule {

    @Binds
    abstract fun bindWithDrawNavigator(navigator: WithDrawNavigatorImpl): WithDrawNavigator
}
