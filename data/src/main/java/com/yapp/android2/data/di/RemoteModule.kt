package com.yapp.android2.data.di

import com.yapp.android2.data.remote.login.LoginRemoteDataSource
import com.yapp.android2.data.remote.login.LoginRemoteDataSourceImpl
import com.yapp.android2.data.remote.record.RecordRemoteDataSource
import com.yapp.android2.data.remote.record.RecordRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RemoteModule {

    @Binds
    @Singleton
    abstract fun provideLoginDataModule(remoteData: LoginRemoteDataSourceImpl) : LoginRemoteDataSource

    @Binds
    @Singleton
    abstract fun provideRecordDataModule(recordRemoteDataSource: RecordRemoteDataSourceImpl): RecordRemoteDataSource
}
