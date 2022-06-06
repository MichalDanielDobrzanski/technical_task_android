package com.michal.technicaltask.data.user

import com.michal.technicaltask.data.user.model.AddNewUserRequestData
import com.michal.technicaltask.data.user.model.UserData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UsersApiService {

    @GET("users")
    suspend fun getAllUsers(): Response<List<UserData>>

    @POST("users")
    suspend fun addNewUser(@Body addNewUserRequestData: AddNewUserRequestData): Response<UserData>

    @DELETE("users/{userId}")
    suspend fun removeUser(@Path("userId") userId: Int): Response<Unit>
}