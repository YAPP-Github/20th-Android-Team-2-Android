package com.yapp.android2.data.di

import com.yapp.android2.data.repository.LoginRepositoryImpl
import com.yapp.android2.data.repository.LogoutRepositoryImpl
import com.yapp.android2.data.repository.NotificationRepositoryImpl
import com.yapp.android2.data.repository.ProductsRepositoryImpl
import com.yapp.android2.data.repository.RecordRepositoryImpl
import com.yapp.android2.data.repository.RemoteConfigRepositoryImpl
import com.yapp.android2.data.repository.SettingRepositoryImpl
import com.yapp.android2.data.repository.WithDrawRepositoryImpl
import com.yapp.android2.domain.repository.Notification.NotificationRepository
import com.yapp.android2.domain.repository.ProductsRepository
import com.yapp.android2.domain.repository.RemoteConfigRepository
import com.yapp.android2.domain.repository.login.LoginRepository
import com.yapp.android2.domain.repository.logout.LogoutRepository
import com.yapp.android2.domain.repository.record.RecordRepository
import com.yapp.android2.domain.repository.setting.SettingRepository
import com.yapp.android2.domain.repository.withdraw.WithDrawRepository
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
    abstract fun bindRemoteConfigRepository(repository: RemoteConfigRepositoryImpl): RemoteConfigRepository

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
    abstract fun bindLogoutRepository(
        repository: LogoutRepositoryImpl
    ): LogoutRepository

    @Binds
    @Singleton
    abstract fun bindWithDrawRepository(
        repository: WithDrawRepositoryImpl
    ): WithDrawRepository

    @Binds
    @Singleton
    abstract fun bindSettingRepository(
        repository: SettingRepositoryImpl
    ): SettingRepository
}
