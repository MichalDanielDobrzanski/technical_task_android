package com.michal.technicaltask.data.time.di

import com.michal.technicaltask.data.time.TimeRepository
import com.michal.technicaltask.data.time.TimeRepositoryImpl
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
    abstract fun bindTimeRepository(timeRepositoryImpl: TimeRepositoryImpl): TimeRepository
}