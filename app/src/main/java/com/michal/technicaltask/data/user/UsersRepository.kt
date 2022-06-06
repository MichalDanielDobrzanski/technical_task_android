package com.michal.technicaltask.data.user

import com.michal.technicaltask.data.user.model.UserData

interface UsersRepository {

    suspend fun getAllUsers(): List<UserData>

    suspend fun addNewUser(name: String, email: String): UserData

    suspend fun removeUser(userId: Int)
}