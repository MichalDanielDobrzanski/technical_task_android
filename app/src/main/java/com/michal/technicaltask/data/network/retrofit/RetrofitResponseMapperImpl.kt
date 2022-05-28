package com.michal.technicaltask.data.network.retrofit

import com.michal.technicaltask.data.network.retrofit.model.HttpApiException
import retrofit2.Response
import javax.inject.Inject

class RetrofitResponseMapperImpl @Inject constructor() : RetrofitResponseMapper {

    @Throws(HttpApiException::class)
    override fun <T> mapHttpResponse(response: Response<T>): T = if (response.isSuccessful) {
        requireNotNull(response.body())
    } else throw HttpApiException(
        errorCode = response.code(),
        message = null
    )

    @Throws(HttpApiException::class)
    override fun <T> mapEmptyHttpResponse(response: Response<T>) {
        if (!response.isSuccessful) {
            throw HttpApiException(
                errorCode = response.code(),
                message = null
            )
        }
    }
}
