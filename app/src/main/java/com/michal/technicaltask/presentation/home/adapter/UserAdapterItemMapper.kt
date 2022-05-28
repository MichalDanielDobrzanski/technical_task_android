package com.michal.technicaltask.presentation.home.adapter

import com.michal.technicaltask.domain.user.all.model.User
import com.michal.technicaltask.domain.user.all.model.Users

interface UserAdapterItemMapper {
    fun map(users: Users): List<UserItem>

    fun mapUser(user: User): UserItem
}