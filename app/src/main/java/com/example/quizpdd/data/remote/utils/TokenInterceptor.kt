package com.example.quizpdd.data.remote.utils

import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor(private val token: String): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(
            chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
        )
    }
}