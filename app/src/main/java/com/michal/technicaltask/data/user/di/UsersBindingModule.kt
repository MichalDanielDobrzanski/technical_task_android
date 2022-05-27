package com.michal.technicaltask.data.user.di

import com.michal.technicaltask.data.user.UsersRepository
import com.michal.technicaltask.data.user.UsersRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UsersBindingModule {

    @Binds
    @Singleton
    abstract fun bindUsersRepository(usersRepositoryImpl: UsersRepositoryImpl): UsersRepository
}