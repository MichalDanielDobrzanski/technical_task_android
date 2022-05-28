package com.michal.technicaltask.presentation.home.adapter

import com.michal.technicaltask.domain.user.all.model.User
import com.michal.technicaltask.domain.user.all.model.Users
import com.michal.time.TimeFormatter
import javax.inject.Inject

class UserAdapterItemMapperImpl @Inject constructor(
    private val timeFormatter: TimeFormatter
) : UserAdapterItemMapper {

    override fun map(users: Users): List<UserItem> {
        return users.users.map(::mapUser)
    }

    override fun mapUser(user: User): UserItem {
        return UserItem(
            id = user.id,
            name = user.name,
            email = user.email,
            creationTimeText = timeFormatter.formatAsRelativeTimeToNow(user.creationDate)
        )
    }
}