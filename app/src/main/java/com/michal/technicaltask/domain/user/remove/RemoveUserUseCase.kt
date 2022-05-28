package com.michal.technicaltask.domain.user.remove

import com.michal.technicaltask.data.user.UsersRepository
import com.michal.technicaltask.domain.base.CompletableParamsUseCase
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

class RemoveUserUseCase @Inject constructor(
    private val usersRepository: UsersRepository
) : CompletableParamsUseCase<Int> {

    override fun execute(params: Int): Completable {
        return usersRepository.removeUser(params)
    }
}