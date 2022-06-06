package com.michal.technicaltask.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.michal.technicaltask.domain.refresh.RefreshContentUseCase
import com.michal.technicaltask.domain.user.all.GetAllUsersUseCase
import com.michal.technicaltask.domain.user.create.AddNewUserUseCase
import com.michal.technicaltask.domain.user.remove.RemoveUserUseCase
import com.michal.technicaltask.presentation.base.BaseViewModel
import com.michal.technicaltask.presentation.home.adapter.UserAdapterItemMapper
import com.michal.technicaltask.presentation.home.adapter.UserItem
import com.michal.technicaltask.presentation.home.model.UsersViewState
import com.michal.technicaltask.presentation.utils.SingleLiveEvent
import com.michal.time.TimeFormatter
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.disposables.Disposable
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllUsersUseCase: GetAllUsersUseCase,
    private val addNewUserUseCase: AddNewUserUseCase,
    private val removeUserUseCase: RemoveUserUseCase,
    private val refreshContentUseCase: RefreshContentUseCase,
    private val timeFormatter: TimeFormatter,
    private val userAdapterItemMapper: UserAdapterItemMapper,
) : BaseViewModel() {

    private val _usersViewState = MutableLiveData<UsersViewState>(UsersViewState.Loading())
    val usersViewState: LiveData<UsersViewState> = _usersViewState

    private var _userItems: MutableList<UserItem> = mutableListOf()

    private val _operationFailedSingleLiveEvent = SingleLiveEvent<Unit>()
    val operationFailedSingleLiveEvent: LiveData<Unit> = _operationFailedSingleLiveEvent

    private var refreshContentDisposable: Disposable? = null

    init {
        getAllUsers()
    }

    fun getAllUsers() {
        viewModelScope.launch {
            _usersViewState.value = UsersViewState.Loading()
            try {
                val users = userAdapterItemMapper.map(getAllUsersUseCase.execute())
                _userItems = users.toMutableList()
                _usersViewState.value = UsersViewState.Content(users)
            } catch (e: Exception) {
                _usersViewState.value = UsersViewState.Error()
                removeRefreshUserListListener()
            }
        }
    }

    fun addNewUser(name: String, email: String) {
        viewModelScope.launch {
            _usersViewState.value = UsersViewState.Loading()
            try {
                val user = userAdapterItemMapper.mapUser(
                    addNewUserUseCase.execute(
                        AddNewUserUseCase.Params(
                            name = name,
                            email = email
                        )
                    )
                )
                _userItems.add(user)
                _usersViewState.value = UsersViewState.Content(_userItems.toList())
            } catch (e: Exception) {
                Timber.e("addNewUser - onError $e")
                _operationFailedSingleLiveEvent.call()
            }
        }
    }

    fun onUserRemoved(userItem: UserItem) {
        viewModelScope.launch {
            _usersViewState.value = UsersViewState.Loading()
            try {
                removeUserUseCase.execute(userItem.id)
                _userItems.removeIf { it.id == userItem.id }
                _usersViewState.value = UsersViewState.Content(_userItems.toList())
            } catch (e: Exception) {
                Timber.e("onUserRemoved - onError $e")
                _operationFailedSingleLiveEvent.call()
            }
        }
    }

    fun listenToRefreshUserList() {
        val disposable = refreshContentUseCase
            .execute()
            .subscribe(
                { refreshUserList() },
                { Timber.e("listenToRefreshContent - onError $it") }
            )
        disposables.add(disposable)
        refreshContentDisposable = disposable
    }

    private fun refreshUserList() {
        val contentValue = _usersViewState.value ?: return
        if (contentValue is UsersViewState.Content) {
            val refreshedUserList = _userItems.map { userItem ->
                userItem.copy(
                    creationRelativeTimeText = timeFormatter.formatAsRelativeTimeToAppStart(userItem.creationDate)
                )
            }
            _userItems = refreshedUserList.toMutableList()
            _usersViewState.value = UsersViewState.Content(refreshedUserList)
        }
    }

    fun removeRefreshUserListListener() {
        refreshContentDisposable?.let {
            disposables.remove(it)
            it.dispose()
        }
    }
}