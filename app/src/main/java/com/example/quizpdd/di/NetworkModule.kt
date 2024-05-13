package com.example.quizpdd.di

import com.example.quizpdd.BuildConfig
import com.example.quizpdd.data.AuthRepositoryImpl
import com.example.quizpdd.data.TopicRepositoryImpl
import com.example.quizpdd.data.datastore.TokenStore
import com.example.quizpdd.data.datastore.UserIDStore
import com.example.quizpdd.data.remote.QuizApi
import com.example.quizpdd.domain.repository.AuthRepository
import com.example.quizpdd.domain.repository.TopicRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val networkModule = module {

    single<AuthRepository> { AuthRepositoryImpl(get(), get(), get()) }
    single<TopicRepository> { TopicRepositoryImpl(get(), get()) }

    single {
        QuizApi(
            baseUrl = "https://ximnpydditfsfkxmchnp.supabase.co/",
            apiKey = BuildConfig.API_KEY,
            tokenStore = get(),
            okHttpClient = null
        )
    }

    single { TokenStore(androidContext()) }
    single { UserIDStore(androidContext()) }
}