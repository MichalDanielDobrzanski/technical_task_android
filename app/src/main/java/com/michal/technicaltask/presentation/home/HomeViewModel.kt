package com.michal.technicaltask.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.michal.technicaltask.data.scheduler.SchedulerProvider
import com.michal.technicaltask.domain.user.all.GetAllUsersUseCase
import com.michal.technicaltask.presentation.base.BaseViewModel
import com.michal.technicaltask.presentation.home.adapter.UserAdapterItemMapper
import com.michal.technicaltask.presentation.home.model.UsersViewState
import com.michal.technicaltask.presentation.utils.plusAssign
import com.michal.technicaltask.presentation.utils.sequentialDisposable
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllUsersUseCase: GetAllUsersUseCase,
    private val schedulerProvider: SchedulerProvider,
    private val userAdapterItemMapper: UserAdapterItemMapper
) : BaseViewModel() {

    private val _usersViewState = MutableLiveData<UsersViewState>(UsersViewState.Loading())
    val usersViewState: LiveData<UsersViewState> = _usersViewState

    private val getAllUsersDisposable by sequentialDisposable(disposables)

    init {
        getAllUsers()
    }

    private fun getAllUsers() {
        getAllUsersDisposable += getAllUsersUseCase
            .execute()
            .map(userAdapterItemMapper::map)
            .observeOn(schedulerProvider.main)
            .subscribe(
                { users ->
                    Timber.e("getAllUsers - onSuccess $users")
                    _usersViewState.value = UsersViewState.Content(users)
                },
                {
                    Timber.e("getAllUsers - onError $it")
                    _usersViewState.value = UsersViewState.Error()
                }
            )

    }

}