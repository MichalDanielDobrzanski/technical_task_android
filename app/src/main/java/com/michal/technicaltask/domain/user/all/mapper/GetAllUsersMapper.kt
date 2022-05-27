package com.michal.technicaltask.domain.user.all.mapper

import com.michal.technicaltask.data.user.model.UserData
import com.michal.technicaltask.domain.user.all.model.Users

fun interface GetAllUsersMapper {
    fun map(usersData: List<UserData>): Users
}