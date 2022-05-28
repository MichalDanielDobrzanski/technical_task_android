package com.michal.technicaltask.data.user

import com.michal.technicaltask.data.user.model.AddNewUserRequestData
import com.michal.technicaltask.data.user.model.UserData
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UsersApiService {

    @GET("users")
    fun getAllUsers(): Single<Response<List<UserData>>>

    @POST("users")
    fun addNewUser(@Body addNewUserRequestData: AddNewUserRequestData): Single<Response<UserData>>

    @DELETE("users/{userId}")
    fun removeUser(@Path("userId") userId: Int): Single<Response<Unit>>
}