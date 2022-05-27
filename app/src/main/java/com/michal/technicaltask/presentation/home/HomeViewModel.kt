package com.michal.technicaltask.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.michal.technicaltask.data.scheduler.SchedulerProvider
import com.michal.technicaltask.domain.user.all.GetAllUsersUseCase
import com.michal.technicaltask.presentation.base.BaseViewModel
import com.michal.technicaltask.presentation.home.model.ListViewState
import com.michal.technicaltask.presentation.utils.plusAssign
import com.michal.technicaltask.presentation.utils.sequentialDisposable
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllUsersUseCase: GetAllUsersUseCase,
    private val schedulerProvider: SchedulerProvider
) : BaseViewModel() {

    private val _listViewState = MutableLiveData<ListViewState>(ListViewState.Loading)
    val listViewState: LiveData<ListViewState> = _listViewState

    private val getAllUsersDisposable by sequentialDisposable(disposables)

    init {
        getAllUsers()
    }

    private fun getAllUsers() {
        getAllUsersDisposable += getAllUsersUseCase
            .execute()
            .observeOn(schedulerProvider.main)
            .subscribe(
                { users ->
                    Timber.e("getAllUsers - onSuccess $users")
                    _listViewState.value = ListViewState.Content(users)
                },
                {
                    Timber.e("getAllUsers - onError $it")
                    _listViewState.value = ListViewState.Error
                }
            )

    }

}