package com.michal.technicaltask.data.user

import com.michal.technicaltask.data.network.retrofit.RetrofitResponseMapper
import com.michal.technicaltask.data.user.model.UserData
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
}