package com.example.quizpdd.di

import com.example.quizpdd.data.AuthRepositoryImpl
import com.example.quizpdd.data.MockQuestionRepository
import com.example.quizpdd.data.MockTopicRepository
import com.example.quizpdd.data.datastore.TokenStore
import com.example.quizpdd.data.remote.QuizApi
import com.example.quizpdd.domain.repository.AuthRepository
import com.example.quizpdd.domain.repository.QuestionRepository
import com.example.quizpdd.domain.repository.TopicRepository
import com.example.quizpdd.ui.auth.login.LoginViewModel
import com.example.quizpdd.ui.auth.register.RegisterViewModel
import com.example.quizpdd.ui.question.QuestionViewModel
import com.example.quizpdd.ui.topic.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<TopicRepository> { MockTopicRepository() }
    single<QuestionRepository> { MockQuestionRepository() }
    viewModel { MainViewModel(get()) }
    viewModel { QuestionViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { RegisterViewModel(get()) }
}

val networkModule = module {

    val apikey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InhpbW5weWRkaXRmc2ZreG1jaG5wIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MTUxOTE3OTMsImV4cCI6MjAzMDc2Nzc5M30.-uS3HuOtkvZThdzknOQaSHX4Lh7LVMqE7g9J42IzveI"

    single<AuthRepository> { AuthRepositoryImpl(get(), get()) }

    single {
        QuizApi(
            baseUrl = "https://ximnpydditfsfkxmchnp.supabase.co/",
            apiKey = apikey,
            token = null,
            okHttpClient = null
        )
    }

    single { TokenStore(androidContext()) }
}