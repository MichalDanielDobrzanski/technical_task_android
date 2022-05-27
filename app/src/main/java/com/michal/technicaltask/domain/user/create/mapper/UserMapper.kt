package com.michal.technicaltask.domain.user.create.mapper

import com.michal.technicaltask.data.user.model.UserData
import com.michal.technicaltask.domain.user.all.model.User

fun interface UserMapper {
    fun map(userData: UserData): User
}