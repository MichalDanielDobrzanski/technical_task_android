package com.michal.technicaltask.presentation.home.adduser.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AddUserParcel(
    val name: String,
    val email: String
) : Parcelable