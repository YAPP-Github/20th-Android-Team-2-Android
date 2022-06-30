package com.yapp.android2.data.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.google.gson.Gson
import com.yapp.android2.data.service.LoginService
import com.yapp.android2.data.service.NotificationService
import com.yapp.android2.data.service.ProductsService
import com.yapp.android2.data.service.RecordService
import com.yapp.android2.data.service.Service
import com.yapp.android2.data.service.interceptor.HeaderInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.create
import timber.log.Timber
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object ServiceModule {

    @Singleton
    @Provides
    fun providesLoginService(
        retrofit: Retrofit
    ): LoginService = retrofit.create()

    @Singleton
    @Provides
    fun providesNotificationService(
        retrofit: Retrofit
    ): NotificationService = retrofit.create()

    @Singleton
    @Provides
    fun providesRecordService(
        retrofit: Retrofit
    ): RecordService = retrofit.create()

    @Singleton
    @Provides
    fun providesProductsService(
        retrofit: Retrofit
    ): ProductsService = retrofit.create()

    @Provides
    @Singleton
    fun provideRetrofit(
        @ApplicationContext context: Context,
        gson: Gson
    ): Retrofit {
        val headerInterceptor = HeaderInterceptor(context, gson)
        val logInterceptor = HttpLoggingInterceptor { message ->
            Timber.tag("OKHttp").d(message)
        }

        val chuckerInterceptor = ChuckerInterceptor
            .Builder(context = context)
            .collector(
                ChuckerCollector(
                    context = context,
                    showNotification = true,
                    retentionPeriod = RetentionManager.Period.ONE_HOUR,
                )
            )
            .maxContentLength(250_000L)
            .alwaysReadResponseBody(true)
            .build()

        return Service.retroBuilder(headerInterceptor, logInterceptor, chuckerInterceptor)
    }
}
