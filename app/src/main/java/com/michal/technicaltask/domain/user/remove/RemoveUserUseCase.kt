package com.michal.technicaltask.domain.user.remove

import com.michal.technicaltask.data.user.UsersRepository
import com.michal.technicaltask.domain.base.VoidUseCase
import javax.inject.Inject

class RemoveUserUseCase @Inject constructor(
    private val usersRepository: UsersRepository
) : VoidUseCase<Int> {

    override suspend fun execute(params: Int) {
        return usersRepository.removeUser(params)
    }
}