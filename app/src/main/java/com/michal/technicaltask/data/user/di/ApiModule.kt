package com.michal.technicaltask.data.user.di

import com.michal.technicaltask.data.user.UsersApiService
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Provides
    @Singleton
    fun provideUsersApiService(
        okHttpClient: OkHttpClient,
        moshi: Moshi
    ): UsersApiService =
        Retrofit.Builder()
            .baseUrl("https://gorest.co.in/public/v2/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
            .create(UsersApiService::class.java)
}