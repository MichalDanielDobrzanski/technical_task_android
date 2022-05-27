package com.michal.technicaltask.domain.user.all.model

import com.michal.technicaltask.data.time.DateItem

data class Users(
    val users: List<User>
)

data class User(
    val name: String,
    val email: String,
    val creationDate: DateItem
)