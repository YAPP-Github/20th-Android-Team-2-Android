package com.yapp.android2.data.di

import android.content.Context
import com.yapp.android2.data.local.BestFriendSharedPreferenceProviderImpl
import com.yapp.android2.data.local.SharedPreferenceProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal class LocalModule {

    @Provides
    fun bindsLocalSharedPreferenceProvide(@ApplicationContext context: Context): SharedPreferenceProvider {
        return BestFriendSharedPreferenceProviderImpl(context)
    }
}
