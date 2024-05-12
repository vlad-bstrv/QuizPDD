package com.example.quizpdd.data.remote

import com.example.quizpdd.data.datastore.TokenStore
import com.example.quizpdd.data.remote.model.AuthResponseDTO
import com.example.quizpdd.data.remote.model.TopicResponse
import com.example.quizpdd.data.remote.utils.ApiKeyInterceptor
import com.example.quizpdd.data.remote.utils.TokenInterceptor
import com.example.quizpdd.domain.model.User
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface QuizApi {
    @POST("auth/v1/token?grant_type=password")
    suspend fun auth(
        @Body user: User
    ): Response<AuthResponseDTO>

    @POST("auth/v1/signup")
    suspend fun register(
        @Body user: User
    ): Response<AuthResponseDTO>

    @GET("rest/v1/progress")
    suspend fun getTopics(
        @Query("user_id") userId: String,
        @Query("select") select: String = "id,topic_id(name,question(id,title)),progress"
    ): Response<List<TopicResponse>>

}

fun QuizApi(
    baseUrl: String,
    apiKey: String,
    tokenStore: TokenStore,
    okHttpClient: OkHttpClient? = null
): QuizApi {
    return retrofit(baseUrl, apiKey, tokenStore, okHttpClient).create()
}

private fun retrofit(
    baseUrl: String,
    apiKey: String,
    tokenStore: TokenStore,
    okHttpClient: OkHttpClient?
): Retrofit {

    val loggingInterceptor = HttpLoggingInterceptor()
    loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

    val token = runBlocking {
        tokenStore.getToken().first()
    }

    val modifiedOkHttpClient = if (token != null) {
        (okHttpClient?.newBuilder() ?: OkHttpClient.Builder())
            .addInterceptor(ApiKeyInterceptor(apiKey))
            .addInterceptor(TokenInterceptor(token))
            .addInterceptor(loggingInterceptor)
            .build()
    } else {
        (okHttpClient?.newBuilder() ?: OkHttpClient.Builder())
            .addInterceptor(ApiKeyInterceptor(apiKey))
            .addInterceptor(loggingInterceptor)
            .build()
    }

    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(modifiedOkHttpClient)
        .build()
}