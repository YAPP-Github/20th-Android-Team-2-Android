package com.yapp.android2.data.di

import android.content.Context
import com.yapp.android2.data.service.LoginService
import com.yapp.android2.data.service.RecordService
import com.yapp.android2.data.service.Service
import com.yapp.android2.data.service.interceptor.HeaderInterceptor
import com.yapp.android2.domain.key.SHARED_PREFERENCE_KEY
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


    @Provides
    @Singleton
    fun provideRetrofit(
        @ApplicationContext context: Context
    ): Retrofit {
        val sharedPreference = context.getSharedPreferences(SHARED_PREFERENCE_KEY, Context.MODE_PRIVATE)
        val authorization = HeaderInterceptor(sharedPreference)

        val log = HttpLoggingInterceptor { message ->
            Timber.tag("OKHttp").d(message)
        }

        return Service.retroBuilder(authorization, log)
    }
}
