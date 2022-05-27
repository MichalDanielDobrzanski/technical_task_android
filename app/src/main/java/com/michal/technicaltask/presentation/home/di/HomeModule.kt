package com.michal.technicaltask.presentation.home.di

import com.michal.technicaltask.data.user.UsersRepository
import com.michal.technicaltask.domain.user.all.GetAllUsersUseCase
import com.michal.technicaltask.domain.user.all.mapper.GetAllUsersMapper
import com.michal.technicaltask.domain.user.all.mapper.GetAllUsersMapperImpl
import com.michal.technicaltask.presentation.home.adapter.UserAdapterItemMapper
import com.michal.technicaltask.presentation.home.adapter.UserAdapterItemMapperImpl
import com.michal.time.TimeFormatter
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
    fun provideUserAdapterItemMapper(
        timeFormatter: TimeFormatter
    ): UserAdapterItemMapper = UserAdapterItemMapperImpl(timeFormatter)

    @Provides
    @ViewModelScoped
    fun provideGetAllUsersMapper(
        timeRepository: com.michal.time.TimeProvider
    ): GetAllUsersMapper = GetAllUsersMapperImpl(timeRepository)

    @Provides
    @ViewModelScoped
    fun provideGetAllUsersUseCase(
        getAllUsersMapper: GetAllUsersMapper,
        usersRepository: UsersRepository
    ): GetAllUsersUseCase = GetAllUsersUseCase(getAllUsersMapper, usersRepository)
}