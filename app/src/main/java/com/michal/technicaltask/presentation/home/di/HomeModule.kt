package com.michal.technicaltask.presentation.home.di

import com.michal.technicaltask.data.time.TimeRepository
import com.michal.technicaltask.data.user.UsersRepository
import com.michal.technicaltask.domain.user.all.GetAllUsersUseCase
import com.michal.technicaltask.domain.user.all.mapper.GetAllUsersMapper
import com.michal.technicaltask.domain.user.all.mapper.GetAllUsersMapperImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object HomeModule {

    @Provides
    @ViewModelScoped
    fun provideGetAllUsersMapper(
        timeRepository: TimeRepository
    ): GetAllUsersMapper = GetAllUsersMapperImpl(timeRepository)

    @Provides
    @ViewModelScoped
    fun provideGetAllUsersUseCase(
        getAllUsersMapper: GetAllUsersMapper,
        usersRepository: UsersRepository
    ): GetAllUsersUseCase = GetAllUsersUseCase(getAllUsersMapper, usersRepository)

}