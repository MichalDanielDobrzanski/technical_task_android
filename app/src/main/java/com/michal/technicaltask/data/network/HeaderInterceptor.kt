package com.michal.technicaltask.data.network

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class HeaderInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .apply {
                addHeader("Content-Type", "application/json")
                addHeader("Accept", "application/json")
            }
            .build()
        return chain.proceed(request)
    }
}