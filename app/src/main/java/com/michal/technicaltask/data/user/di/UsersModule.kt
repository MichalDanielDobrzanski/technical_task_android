package com.michal.technicaltask.data.user.di

import com.michal.technicaltask.data.network.retrofit.RetrofitResponseMapper
import com.michal.technicaltask.data.user.UsersApiService
import com.michal.technicaltask.data.user.UsersRepository
import com.michal.technicaltask.data.user.UsersRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UsersModule {

    @Provides
    @Singleton
    fun provideUsersRepository(
        usersApiService: UsersApiService,
        retrofitResponseMapper: RetrofitResponseMapper
    ): UsersRepository = UsersRepositoryImpl(usersApiService, retrofitResponseMapper)
}