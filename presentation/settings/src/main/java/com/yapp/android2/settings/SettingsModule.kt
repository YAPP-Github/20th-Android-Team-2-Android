package com.yapp.android2.settings

import com.best.friends.navigator.SettingNavigator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class SettingsModule {

    @Binds
    abstract fun bindSettingsNavigator(navi: SettingsNavigatorImpl): SettingNavigator

}
