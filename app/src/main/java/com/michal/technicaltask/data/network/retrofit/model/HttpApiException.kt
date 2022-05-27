package com.michal.technicaltask.data.network.retrofit.model

/**
 * Thrown when HTTP API call was unsuccessful.
 */
class HttpApiException(
    val errorCode: Int,
    override val message: String?
) : Exception() {
    override fun toString(): String = "HttpApiException(errorCode=$errorCode, message=$message)"
}
