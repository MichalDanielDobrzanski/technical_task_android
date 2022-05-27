package com.michal.technicaltask.data.user

import com.michal.technicaltask.data.user.model.UserData
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.GET

interface UsersApiService {

    @GET("users")
    fun getAllUsers(): Single<Response<List<UserData>>>
}