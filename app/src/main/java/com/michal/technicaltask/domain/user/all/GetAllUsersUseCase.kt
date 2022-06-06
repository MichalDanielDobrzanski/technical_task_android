package com.michal.technicaltask.domain.user.all

import com.michal.technicaltask.data.user.UsersRepository
import com.michal.technicaltask.domain.base.UseCase
import com.michal.technicaltask.domain.user.all.mapper.GetAllUsersMapper
import com.michal.technicaltask.domain.user.all.model.Users
import javax.inject.Inject

class GetAllUsersUseCase @Inject constructor(
    private val getAllUsersMapper: GetAllUsersMapper,
    private val usersRepository: UsersRepository
) : UseCase<Users> {

    override suspend fun execute(): Users {
        return getAllUsersMapper.map(usersRepository.getAllUsers())
    }
}