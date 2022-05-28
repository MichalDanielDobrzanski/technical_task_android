package com.michal.technicaltask.presentation.home.di

import com.michal.technicaltask.data.scheduler.SchedulerProvider
import com.michal.technicaltask.data.user.UsersRepository
import com.michal.technicaltask.domain.refresh.RefreshContentUseCase
import com.michal.technicaltask.domain.user.all.GetAllUsersUseCase
import com.michal.technicaltask.domain.user.all.mapper.GetAllUsersMapper
import com.michal.technicaltask.domain.user.all.mapper.GetAllUsersMapperImpl
import com.michal.technicaltask.domain.user.create.AddNewUserUseCase
import com.michal.technicaltask.domain.user.create.mapper.UserMapper
import com.michal.technicaltask.domain.user.create.mapper.UserMapperImpl
import com.michal.technicaltask.domain.user.remove.RemoveUserUseCase
import com.michal.technicaltask.presentation.home.adapter.UserAdapterItemMapper
import com.michal.technicaltask.presentation.home.adapter.UserAdapterItemMapperImpl
import com.michal.time.TimeFormatter
import com.michal.time.TimeProvider
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
        userMapper: UserMapper,
    ): GetAllUsersMapper = GetAllUsersMapperImpl(userMapper)

    @Provides
    @ViewModelScoped
    fun providerUserMapper(
        timeRepository: TimeProvider
    ): UserMapper = UserMapperImpl(timeRepository)

    @Provides
    @ViewModelScoped
    fun provideGetAllUsersUseCase(
        getAllUsersMapper: GetAllUsersMapper,
        usersRepository: UsersRepository
    ): GetAllUsersUseCase = GetAllUsersUseCase(getAllUsersMapper, usersRepository)

    @Provides
    @ViewModelScoped
    fun provideAddNewUserUseCase(
        usersRepository: UsersRepository,
        userMapper: UserMapper
    ): AddNewUserUseCase = AddNewUserUseCase(usersRepository, userMapper)

    @Provides
    @ViewModelScoped
    fun provideRemoveUserUseCase(
        usersRepository: UsersRepository,
    ): RemoveUserUseCase = RemoveUserUseCase(usersRepository)

    @Provides
    @ViewModelScoped
    fun provideRefreshContentUseCase(
        schedulerProvider: SchedulerProvider
    ): RefreshContentUseCase = RefreshContentUseCase(schedulerProvider)
}