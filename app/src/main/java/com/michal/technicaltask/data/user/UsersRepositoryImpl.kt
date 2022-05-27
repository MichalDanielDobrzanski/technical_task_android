package com.michal.technicaltask.data.user

import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(
    private val usersApiService: UsersApiService
) : UsersRepository {
}