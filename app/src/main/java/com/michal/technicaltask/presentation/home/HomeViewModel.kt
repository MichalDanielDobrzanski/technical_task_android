package com.michal.technicaltask.presentation.home

import com.michal.technicaltask.domain.user.get.GetAllUsersUseCase
import com.michal.technicaltask.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllUsersUseCase: GetAllUsersUseCase
) : BaseViewModel() {
}