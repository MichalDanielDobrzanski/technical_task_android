package com.michal.technicaltask.presentation.home.adapter

import com.michal.time.model.DateItem

data class UserItem(
    val id: Int,
    val name: String,
    val email: String,
    val creationDate: DateItem,
    val creationRelativeTimeText: String
)