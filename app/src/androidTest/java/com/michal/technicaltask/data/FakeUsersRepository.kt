package com.michal.technicaltask.data

import com.michal.technicaltask.data.user.UsersRepository
import com.michal.technicaltask.data.user.model.UserData
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class FakeUsersRepository : UsersRepository {

    private val users = usersTestData.toMutableList()

    override fun getAllUsers(): Single<List<UserData>> = Single.just(users)

    override fun addNewUser(name: String, email: String): Single<UserData> {
        return Single.fromCallable {
            val index = if (users.isNotEmpty()) users.lastIndex else 0
            val user = UserData(index, name, email)
            users.add(user)
            user
        }
    }

    override fun removeUser(userId: Int): Completable = Completable.fromCallable {
        users.removeIf { it.id == userId }
    }

    companion object {
        val usersTestData = listOf(
            UserData(1, "test1", "test1@email.com"),
            UserData(2, "test2", "test2@email.com"),
            UserData(3, "test3", "test3@email.com")
        )
    }
}