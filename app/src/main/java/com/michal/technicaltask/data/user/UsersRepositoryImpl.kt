package com.michal.technicaltask.data.user

import com.michal.technicaltask.data.network.retrofit.RetrofitResponseMapper
import com.michal.technicaltask.data.user.model.AddNewUserRequestData
import com.michal.technicaltask.data.user.model.UserData
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(
    private val usersApiService: UsersApiService,
    private val retrofitResponseMapper: RetrofitResponseMapper,
) : UsersRepository {

    override suspend fun getAllUsers(): List<UserData> {
        return retrofitResponseMapper.mapHttpResponse(usersApiService.getAllUsers())
    }

    override suspend fun addNewUser(name: String, email: String): UserData {
        val requestData = AddNewUserRequestData(
            name = name,
            email = email,
            gender = FALLBACK_GENDER,
            status = FALLBACK_STATUS
        )
        return retrofitResponseMapper.mapHttpResponse(usersApiService.addNewUser(requestData))
    }

    override suspend fun removeUser(userId: Int) {
        retrofitResponseMapper.mapEmptyHttpResponse(
            usersApiService.removeUser(userId)
        )
    }
}

private const val FALLBACK_GENDER = "male"
private const val FALLBACK_STATUS = "active"