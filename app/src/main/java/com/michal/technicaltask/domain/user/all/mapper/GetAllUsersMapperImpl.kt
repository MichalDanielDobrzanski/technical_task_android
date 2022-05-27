package com.michal.technicaltask.domain.user.all.mapper

import com.michal.technicaltask.data.user.model.UserData
import com.michal.technicaltask.domain.user.all.model.User
import com.michal.technicaltask.domain.user.all.model.Users
import com.michal.time.TimeProvider
import javax.inject.Inject

class GetAllUsersMapperImpl @Inject constructor(
    private val timeRepository: TimeProvider
) : GetAllUsersMapper {

    override fun map(usersData: List<UserData>): Users {
        return Users(
            users = usersData.map { user ->
                User(
                    id = user.id,
                    name = user.name,
                    email = user.email,
                    creationDate = timeRepository.getNow()
                )
            }
        )
    }

}