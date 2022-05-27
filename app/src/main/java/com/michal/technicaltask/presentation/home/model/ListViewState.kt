package com.michal.technicaltask.presentation.home.model

sealed class ListViewState {

    object Loading: ListViewState()

    data class Content(val data: Any): ListViewState()

    object Error: ListViewState()
}