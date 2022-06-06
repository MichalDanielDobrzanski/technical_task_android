package com.michal.technicaltask.domain.user.create

import com.michal.technicaltask.data.user.UsersRepository
import com.michal.technicaltask.domain.base.ParamsUseCase
import com.michal.technicaltask.domain.user.all.model.User
import com.michal.technicaltask.domain.user.create.mapper.UserMapper
import javax.inject.Inject

class AddNewUserUseCase @Inject constructor(
    private val usersRepository: UsersRepository,
    private val userMapper: UserMapper
) : ParamsUseCase<AddNewUserUseCase.Params, User> {

    override suspend fun execute(params: Params): User {
        return userMapper.map(
            usersRepository.addNewUser(
                name = params.name,
                email = params.email
            )
        )
    }

    data class Params(
        val name: String,
        val email: String
    )

}