package com.example.quizpdd.data.utils

import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor(private val apikey: String): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(
            chain.request().newBuilder()
                .addHeader("apikey", apikey)
                .build()
        )
    }
}