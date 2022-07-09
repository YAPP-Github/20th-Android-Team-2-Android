package com.yapp.android2.data.di

import android.content.Context
import com.google.gson.Gson
import com.yapp.android2.data.local.BestFriendSharedPreferenceProviderImpl
import com.yapp.android2.data.local.SharedPreferenceProvider
import com.yapp.android2.data.local.login.LoginLocalDataSource
import com.yapp.android2.data.local.login.LoginLocalDataSourceImpl
import com.yapp.android2.data.local.notification.NotificationLocalDataSource
import com.yapp.android2.data.local.notification.NotificationLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal class LocalModule {

    @Provides
    fun bindsLocalSharedPreferenceProvide(
        @ApplicationContext context: Context,
        gson: Gson
    ): SharedPreferenceProvider {
        return BestFriendSharedPreferenceProviderImpl(context, gson)
    }

    @Provides
    fun provideLoginLocalDataModule(sharedPreference: BestFriendSharedPreferenceProviderImpl): LoginLocalDataSource =
        LoginLocalDataSourceImpl(sharedPreference)

    @Provides
    fun provideNotificationLocalDataModule(sharedPreference: BestFriendSharedPreferenceProviderImpl): NotificationLocalDataSource =
        NotificationLocalDataSourceImpl(sharedPreference)
}
