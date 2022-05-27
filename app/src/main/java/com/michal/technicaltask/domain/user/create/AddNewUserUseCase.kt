package com.michal.technicaltask.domain.user.create

import com.michal.technicaltask.data.user.UsersRepository
import com.michal.technicaltask.domain.base.SingleParamsUseCase
import com.michal.technicaltask.domain.user.all.model.User
import com.michal.technicaltask.domain.user.create.mapper.UserMapper
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class AddNewUserUseCase @Inject constructor(
    private val usersRepository: UsersRepository,
    private val userMapper: UserMapper
) : SingleParamsUseCase<AddNewUserUseCase.Params, User> {

    override fun execute(params: Params): Single<User> {
        return usersRepository.addNewUser(
            name = params.name,
            email = params.email
        )
            .map(userMapper::map)
    }

    data class Params(
        val name: String,
        val email: String
    )

}