package com.michal.technicaltask.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.michal.technicaltask.data.scheduler.SchedulerProvider
import com.michal.technicaltask.domain.user.all.GetAllUsersUseCase
import com.michal.technicaltask.domain.user.create.AddNewUserUseCase
import com.michal.technicaltask.domain.user.remove.RemoveUserUseCase
import com.michal.technicaltask.presentation.base.BaseViewModel
import com.michal.technicaltask.presentation.home.adapter.UserAdapterItemMapper
import com.michal.technicaltask.presentation.home.adapter.UserItem
import com.michal.technicaltask.presentation.home.model.UsersViewState
import com.michal.technicaltask.presentation.utils.SingleLiveEvent
import com.michal.technicaltask.presentation.utils.plusAssign
import com.michal.technicaltask.presentation.utils.sequentialDisposable
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllUsersUseCase: GetAllUsersUseCase,
    private val addNewUserUseCase: AddNewUserUseCase,
    private val removeUserUseCase: RemoveUserUseCase,
    private val schedulerProvider: SchedulerProvider,
    private val userAdapterItemMapper: UserAdapterItemMapper
) : BaseViewModel() {

    private val _usersViewState = MutableLiveData<UsersViewState>(UsersViewState.Loading())
    val usersViewState: LiveData<UsersViewState> = _usersViewState

    private var _userItems: MutableList<UserItem> = mutableListOf()

    private val _operationFailedSingleLiveEvent = SingleLiveEvent<Unit>()
    val operationFailedSingleLiveEvent: LiveData<Unit> = _operationFailedSingleLiveEvent

    private val getAllUsersDisposable by sequentialDisposable(disposables)
    private val addNewUserDisposable by sequentialDisposable(disposables)
    private val removeUserDisposable by sequentialDisposable(disposables)

    init {
        getAllUsers()
    }

    fun getAllUsers() {
        getAllUsersDisposable += getAllUsersUseCase
            .execute()
            .map(userAdapterItemMapper::map)
            .observeOn(schedulerProvider.main)
            .doOnSubscribe { _usersViewState.value = UsersViewState.Loading() }
            .subscribe(
                { users ->
                    Timber.v("getAllUsers - onSuccess $users")
                    _userItems = users.toMutableList()
                    _usersViewState.value = UsersViewState.Content(users)
                },
                {
                    Timber.e("getAllUsers - onError $it")
                    _usersViewState.value = UsersViewState.Error()
                }
            )

    }

    fun addNewUser(name: String, email: String) {
        addNewUserDisposable += addNewUserUseCase
            .execute(
                AddNewUserUseCase.Params(
                    name = name,
                    email = email
                )
            )
            .map(userAdapterItemMapper::mapUser)
            .observeOn(schedulerProvider.main)
            .doOnSubscribe { _usersViewState.value = UsersViewState.Loading() }
            .subscribe(
                { userItem ->
                    Timber.v("addNewUser - onComplete")
                    _userItems.add(userItem)
                    _usersViewState.value = UsersViewState.Content(_userItems.toList())
                },
                {
                    Timber.e("addNewUser - onError $it")
                    _operationFailedSingleLiveEvent.call()
                    _usersViewState.value = UsersViewState.Content(_userItems)
                }
            )
    }

    fun onUserRemoved(userItem: UserItem) {
        removeUserDisposable += removeUserUseCase
            .execute(userItem.id)
            .observeOn(schedulerProvider.main)
            .doOnSubscribe { _usersViewState.value = UsersViewState.Loading() }
            .subscribe(
                {
                    Timber.v("addNewUser - onComplete")
                    _userItems.remove(userItem)
                    _usersViewState.value = UsersViewState.Content(_userItems.toList())
                },
                {
                    Timber.e("addNewUser - onError $it")
                    _operationFailedSingleLiveEvent.call()
                    _usersViewState.value = UsersViewState.Content(_userItems)
                }
            )
    }
}