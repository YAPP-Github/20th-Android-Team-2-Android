package com.yapp.android2.data.di

import com.yapp.android2.data.service.LoginService
import com.yapp.android2.data.service.Service
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object ServiceModule {

    @Singleton
    @Provides
    fun providesLoginService(
        retrofit: Retrofit
    ): LoginService = retrofit.create()


    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Service.retroBuilder()
    }
}