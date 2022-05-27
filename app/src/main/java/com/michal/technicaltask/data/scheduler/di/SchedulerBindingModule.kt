package com.michal.technicaltask.data.scheduler.di

import com.michal.technicaltask.data.scheduler.SchedulerProvider
import com.michal.technicaltask.data.scheduler.SchedulerProviderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class SchedulerBindingModule {

    @Binds
    abstract fun bindSchedulerProvider(schedulerProviderImpl: SchedulerProviderImpl): SchedulerProvider
}