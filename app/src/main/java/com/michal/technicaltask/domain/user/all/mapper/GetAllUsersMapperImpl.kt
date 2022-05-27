package com.michal.technicaltask.domain.user.all.mapper

import com.michal.technicaltask.data.time.TimeRepository
import com.michal.technicaltask.data.user.model.UserData
import com.michal.technicaltask.domain.user.all.model.User
import com.michal.technicaltask.domain.user.all.model.Users
import javax.inject.Inject

class GetAllUsersMapperImpl @Inject constructor(
    private val timeRepository: TimeRepository
) : GetAllUsersMapper {

    override fun map(usersData: List<UserData>): Users {
        return Users(
            users = usersData.map { user ->
                User(
                    name = user.name,
                    email = user.email,
                    creationDate = timeRepository.getNow()
                )
            }
        )
    }

}