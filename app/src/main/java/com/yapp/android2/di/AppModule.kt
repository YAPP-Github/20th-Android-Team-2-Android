package com.yapp.android2.di

import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.yapp.android2.domain.key.POLICY_WEB_URL_KEY
import com.yapp.android2.domain.url.WebURL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun bindsFirebaseMessagingProvide(): FirebaseMessaging = FirebaseMessaging.getInstance()
    
    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder().create()
    }

    @Provides
    @Singleton
    @Named(POLICY_WEB_URL_KEY)
    fun providesWebURL(): WebURL {
        /**
         * 기존 raw url
         * @param = https://rustic-decade-83c.notion.site/cdfb1587f8854c7e86a2ee46ec471311
         **/

        return WebURL("https://bit.ly/3yn5yL4")
    }
}
