package com.michal.technicaltask.domain.user.all.mapper

import com.michal.technicaltask.data.user.model.UserData
import com.michal.technicaltask.domain.user.all.model.Users
import com.michal.technicaltask.domain.user.create.mapper.UserMapper
import javax.inject.Inject

class GetAllUsersMapperImpl @Inject constructor(
    private val userMapper: UserMapper
) : GetAllUsersMapper {

    override fun map(usersData: List<UserData>): Users {
        return Users(
            users = usersData.map(userMapper::map)
        )
    }

}