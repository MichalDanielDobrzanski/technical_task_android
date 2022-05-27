package com.michal.technicaltask.data.network.di

import com.michal.technicaltask.data.network.AuthInterceptor
import com.michal.technicaltask.data.network.HeaderInterceptor
import com.michal.technicaltask.data.network.LoggingInterceptor
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder().build()

    @Provides
    @Singleton
    fun provideOkHttpClient(
        authInterceptor: AuthInterceptor,
        headerInterceptor: HeaderInterceptor,
        loggingInterceptor: LoggingInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor(loggingInterceptor).setLevel(HttpLoggingInterceptor.Level.BODY))
        .addInterceptor(headerInterceptor)
        .addInterceptor(authInterceptor)
        .build()
}