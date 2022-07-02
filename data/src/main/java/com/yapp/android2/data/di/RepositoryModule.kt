package com.yapp.android2.data.di

import com.yapp.android2.data.repository.*
import com.yapp.android2.domain.repository.Notification.NotificationRepository
import com.yapp.android2.domain.repository.ProductsRepository
import com.yapp.android2.domain.repository.login.LoginRepository
import com.yapp.android2.domain.repository.record.RecordRepository
import com.yapp.android2.domain.repository.setting.SettingRepository
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

    @Binds
    @Singleton
    abstract fun bindsNotificationRepository(repository: NotificationRepositoryImpl): NotificationRepository

    @Binds
    @Singleton
    abstract fun bindsRecordRepository(
        repository: RecordRepositoryImpl
    ): RecordRepository

    @Binds
    @Singleton
    abstract fun bindsProductsRepository(
        repository: ProductsRepositoryImpl
    ): ProductsRepository

    @Binds
    @Singleton
    abstract fun bindSettingRepository(
        repository: SettingRepositoryImpl
    ): SettingRepository
}
