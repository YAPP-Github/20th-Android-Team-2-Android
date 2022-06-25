package com.yapp.android2.data.di

import com.yapp.android2.data.remote.login.LoginRemoteDataSource
import com.yapp.android2.data.remote.login.LoginRemoteDataSourceImpl
import com.yapp.android2.data.remote.notification.NotificationRemoteDataSource
import com.yapp.android2.data.remote.notification.NotificationRemoteDataSourceImpl
import com.yapp.android2.data.remote.products.ProductsRemoteDataSource
import com.yapp.android2.data.remote.products.ProductsRemoteDataSourceImpl
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
    abstract fun provideNotificationDataModule(remoteData: NotificationRemoteDataSourceImpl) : NotificationRemoteDataSource

    @Binds
    @Singleton
    abstract fun provideRecordDataModule(
        recordRemoteDataSource: RecordRemoteDataSourceImpl
    ): RecordRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindProductsRemoteDataSource(
        dataSource: ProductsRemoteDataSourceImpl
    ): ProductsRemoteDataSource
}
