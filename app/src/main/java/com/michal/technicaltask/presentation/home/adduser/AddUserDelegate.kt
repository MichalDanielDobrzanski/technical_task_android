package com.michal.technicaltask.presentation.home.adduser

import android.util.Patterns
import javax.inject.Inject

class AddUserDelegate @Inject constructor() {

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