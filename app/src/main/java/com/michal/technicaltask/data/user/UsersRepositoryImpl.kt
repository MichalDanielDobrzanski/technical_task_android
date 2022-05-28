package com.michal.technicaltask.data.user

import com.michal.technicaltask.data.network.retrofit.RetrofitResponseMapper
import com.michal.technicaltask.data.user.model.AddNewUserRequestData
import com.michal.technicaltask.data.user.model.UserData
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(
    private val usersApiService: UsersApiService,
    private val retrofitResponseMapper: RetrofitResponseMapper,
) : UsersRepository {

    override fun getAllUsers(): Single<List<UserData>> {
        return usersApiService.getAllUsers()
            .map(retrofitResponseMapper::mapHttpResponse)
    }

    override fun addNewUser(name: String, email: String): Single<UserData> {
        val requestData = AddNewUserRequestData(
            name = name,
            email = email,
            gender = FALLBACK_GENDER,
            status = FALLBACK_STATUS
        )
        return usersApiService.addNewUser(requestData)
            .map(retrofitResponseMapper::mapHttpResponse)
    }

    override fun removeUser(userId: Int): Completable {
        return usersApiService.removeUser(userId)
            .map { response ->
                retrofitResponseMapper.mapEmptyHttpResponse(response)
            }
            .ignoreElement()
    }
}

private const val FALLBACK_GENDER = "male"
private const val FALLBACK_STATUS = "active"