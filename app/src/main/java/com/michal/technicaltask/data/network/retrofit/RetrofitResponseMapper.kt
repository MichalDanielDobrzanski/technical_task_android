package com.michal.technicaltask.data.network.retrofit

import com.michal.technicaltask.data.network.retrofit.model.HttpApiException
import retrofit2.Response

interface RetrofitResponseMapper {
    /**
     * Maps the response to a class of [T]. Throws error for unsuccessful requests.
     * @param response [Response] in question.
     * @return Response's body if it was successful.
     * @throws HttpApiException when response was unsuccessful.
     */
    @Throws(HttpApiException::class)
    fun <T> mapHttpResponse(response: Response<T>): T

    @Throws(HttpApiException::class)
    fun <T> mapEmptyHttpResponse(response: Response<T>)
}
