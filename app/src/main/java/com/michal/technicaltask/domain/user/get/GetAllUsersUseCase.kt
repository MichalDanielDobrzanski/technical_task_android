package com.michal.technicaltask.domain.user.get

import com.michal.technicaltask.data.user.UsersRepository
import javax.inject.Inject

class GetAllUsersUseCase @Inject constructor(
    private val usersRepository: UsersRepository
) {
}