package com.michal.technicaltask.domain.user.create.mapper

import com.michal.technicaltask.data.user.model.UserData
import com.michal.technicaltask.domain.user.all.model.User
import com.michal.time.TimeProvider
import javax.inject.Inject

class UserMapperImpl @Inject constructor(
    private val timeProvider: TimeProvider
) : UserMapper {

    override fun map(userData: UserData): User {
        return User(
            id = userData.id,
            name = userData.name,
            email = userData.email,
            creationDate = timeProvider.getNow()
        )
    }

}