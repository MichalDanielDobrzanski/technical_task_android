package com.michal.technicaltask.data.network.di

import com.michal.technicaltask.data.network.retrofit.RetrofitResponseMapper
import com.michal.technicaltask.data.network.retrofit.RetrofitResponseMapperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkBindingModule {

    @Binds
    abstract fun bindRetrofitResponseMapper(retrofitResponseMapperImpl: RetrofitResponseMapperImpl): RetrofitResponseMapper
}