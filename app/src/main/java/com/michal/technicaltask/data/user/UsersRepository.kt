package com.michal.technicaltask.data.user

import com.michal.technicaltask.data.user.model.UserData
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface UsersRepository {

    fun getAllUsers(): Single<List<UserData>>

    fun addNewUser(name: String, email: String): Single<UserData>

    fun removeUser(userId: Int): Completable
}