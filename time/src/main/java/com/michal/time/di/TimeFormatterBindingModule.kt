package com.michal.time.di

import com.michal.time.TimeFormatter
import com.michal.time.TimeFormatterImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class TimeFormatterBindingModule {

    @Binds
    @Singleton
    abstract fun bindTimeFormatter(timeFormatterImpl: TimeFormatterImpl): TimeFormatter
}