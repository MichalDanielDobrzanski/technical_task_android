package com.michal.technicaltask.domain.user.all.model

import com.michal.time.model.DateItem

data class Users(
    val users: List<User>
)

data class User(
    val id: Int,
    val name: String,
    val email: String,
    val creationDate: DateItem
)