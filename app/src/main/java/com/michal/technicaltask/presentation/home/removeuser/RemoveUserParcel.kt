package com.michal.technicaltask.presentation.home.removeuser

import android.os.Parcelable
import com.michal.technicaltask.presentation.home.adapter.UserItem
import kotlinx.parcelize.Parcelize

@Parcelize
data class RemoveUserParcel(
    val id: Int,
    val name: String,
    val email: String,
    val creationTimeText: String
) : Parcelable {

    fun toUserItem(): UserItem {
        return with(this) {
            UserItem(
                id = id,
                name = name,
                email = email,
                creationTimeText = creationTimeText
            )
        }
    }

    companion object {
        fun fromUserItem(userItem: UserItem): RemoveUserParcel {
            return with(userItem) {
                RemoveUserParcel(
                    id = id,
                    name = name,
                    email = email,
                    creationTimeText = creationTimeText
                )
            }
        }
    }
}