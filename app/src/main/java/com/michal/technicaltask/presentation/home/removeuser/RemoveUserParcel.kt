package com.michal.technicaltask.presentation.home.removeuser

import android.os.Parcelable
import com.michal.technicaltask.presentation.home.adapter.UserItem
import com.michal.time.model.DateItem
import kotlinx.parcelize.Parcelize

@Parcelize
data class RemoveUserParcel(
    val id: Int,
    val name: String,
    val email: String,
    val creationDate: DateItem,
    val creationTimeText: String
) : Parcelable {

    fun toUserItem(): UserItem {
        return with(this) {
            UserItem(
                id = id,
                name = name,
                email = email,
                creationDate = creationDate,
                creationRelativeTimeText = creationTimeText
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
                    creationDate = creationDate,
                    creationTimeText = creationRelativeTimeText
                )
            }
        }
    }
}