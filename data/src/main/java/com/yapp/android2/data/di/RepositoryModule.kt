package com.yapp.android2.data.di

import com.yapp.android2.data.repository.LoginRepositoryImpl
import com.yapp.android2.domain.repository.login.LoginRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindsLoginRepository(repository: LoginRepositoryImpl): LoginRepository
}
