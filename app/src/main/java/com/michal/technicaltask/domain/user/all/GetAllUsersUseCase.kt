package com.michal.technicaltask.domain.user.all

import com.michal.technicaltask.data.user.UsersRepository
import com.michal.technicaltask.domain.base.SingleUseCase
import com.michal.technicaltask.domain.user.all.mapper.GetAllUsersMapper
import com.michal.technicaltask.domain.user.all.model.Users
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetAllUsersUseCase @Inject constructor(
    private val getAllUsersMapper: GetAllUsersMapper,
    private val usersRepository: UsersRepository
) : SingleUseCase<Users> {

    override fun execute(): Single<Users> {
        return usersRepository.getAllUsers()
            .map(getAllUsersMapper::map)
    }
}