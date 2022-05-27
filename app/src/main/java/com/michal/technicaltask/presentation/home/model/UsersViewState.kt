package com.michal.technicaltask.presentation.home.model

import com.michal.technicaltask.presentation.home.adapter.UserItem

sealed class UsersViewState {

    object Loading : UsersViewState()

    data class Content(val userItems: List<UserItem>) : UsersViewState()

    object Error : UsersViewState()
}