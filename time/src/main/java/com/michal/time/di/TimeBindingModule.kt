package com.michal.time.di

import com.michal.time.TimeProvider
import com.michal.time.TimeProviderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class TimeBindingModule {

    @Binds
    @Singleton
    abstract fun bindTimeRepository(timeRepositoryImpl: TimeProviderImpl): TimeProvider
}