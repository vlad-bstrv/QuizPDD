package com.example.quizpdd.data

import com.example.quizpdd.data.model.AuthResponseDTO
import com.example.quizpdd.data.utils.ApiKeyInterceptor
import com.example.quizpdd.data.utils.TokenInterceptor
import com.example.quizpdd.domain.model.User
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.Body
import retrofit2.http.POST

interface QuizApi {
    @POST("auth/v1/signup")
    suspend fun auth(
        @Body user: User
    ): Response<AuthResponseDTO>

    @POST("auth/v1/signup")
    suspend fun register(
        @Body user: User
    ): Response<AuthResponseDTO>
}

fun QuizApi(
    baseUrl: String,
    apiKey: String,
    token: String?,
    okHttpClient: OkHttpClient? = null
): QuizApi {
    return retrofit(baseUrl, apiKey, token, okHttpClient).create()
}

private fun retrofit(
    baseUrl: String,
    apiKey: String,
    token: String?,
    okHttpClient: OkHttpClient?
): Retrofit {

    val modifiedOkHttpClient = if (token != null) {
        (okHttpClient?.newBuilder() ?: OkHttpClient.Builder())
            .addInterceptor(ApiKeyInterceptor(apiKey))
            .addInterceptor(TokenInterceptor(token))
            .build()
    } else {
        (okHttpClient?.newBuilder() ?: OkHttpClient.Builder())
            .addInterceptor(ApiKeyInterceptor(apiKey))
            .build()
    }


    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(modifiedOkHttpClient)
        .build()
}