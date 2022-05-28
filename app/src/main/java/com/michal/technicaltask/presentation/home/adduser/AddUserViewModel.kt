package com.michal.technicaltask.presentation.home.adduser

import android.util.Patterns
import com.michal.technicaltask.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddUserViewModel @Inject constructor() : BaseViewModel() {

    fun validateNameAndEmail(
        currentName: String?,
        currentEmail: String?,
        onInvalidName: () -> Unit,
        onInvalidEmail: () -> Unit,
        onValidData: (String, String) -> Unit
    ) {
        if (!isValidName(currentName)) {
            onInvalidName.invoke()
        } else if (!isValidEmail(currentEmail)) {
            onInvalidEmail.invoke()
        } else {
            onValidData(currentName!!, currentEmail!!)
        }
    }

    fun isValidName(name: String?) = !name.isNullOrBlank() && name.isNotEmpty()

    fun isValidEmail(email: String?): Boolean {
        return !email.isNullOrBlank() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

}