package com.michal.technicaltask.presentation.home.di

import com.michal.technicaltask.data.user.UsersRepository
import com.michal.technicaltask.domain.user.get.GetAllUsersUseCase
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
    fun provideGetAllUsersUseCase(
        usersRepository: UsersRepository
    ): GetAllUsersUseCase = GetAllUsersUseCase(usersRepository)

}