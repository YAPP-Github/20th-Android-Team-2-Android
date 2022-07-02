package com.yapp.android2.settings

import com.best.friends.navigator.PolicyNavigator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
internal abstract class PolicyNavigatorModule {
    @Binds
    abstract fun bindsPolicyNavigator(navigator: PolicyNavigatorImpl): PolicyNavigator
}
