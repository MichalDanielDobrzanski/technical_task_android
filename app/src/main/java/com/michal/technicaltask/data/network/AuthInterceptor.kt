package com.michal.technicaltask.data.network

import com.michal.technicaltask.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val apiKey = BuildConfig.API_KEY
        val request = chain.request().newBuilder()
            .apply {
                addHeader("Authorization", "Bearer $apiKey")
            }
            .build()

        return chain.proceed(request)
    }
}