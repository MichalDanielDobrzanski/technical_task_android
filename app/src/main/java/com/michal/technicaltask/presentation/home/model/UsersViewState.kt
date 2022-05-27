package com.michal.technicaltask.presentation.home.model

import com.michal.technicaltask.presentation.home.adapter.UserItem

sealed class UsersViewState {

    abstract val loaderVisible: Boolean
    abstract val contentVisible: Boolean
    abstract val emptyContentVisible: Boolean
    abstract val errorVisible: Boolean

    data class Loading(
        override val loaderVisible: Boolean = true,
        override val contentVisible: Boolean = false,
        override val emptyContentVisible: Boolean = false,
        override val errorVisible: Boolean = false
    ) : UsersViewState()

    data class Content(
        val userItems: List<UserItem>,
        override val loaderVisible: Boolean = false,
        override val errorVisible: Boolean = false,
        override val contentVisible: Boolean = userItems.isNotEmpty(),
        override val emptyContentVisible: Boolean = userItems.isEmpty()
    ) : UsersViewState()

    data class Error(
        override val loaderVisible: Boolean = false,
        override val contentVisible: Boolean = false,
        override val emptyContentVisible: Boolean = false,
        override val errorVisible: Boolean = true
    ) : UsersViewState()
}