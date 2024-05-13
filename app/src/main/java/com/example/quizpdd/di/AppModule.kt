package com.example.quizpdd.di

import com.example.quizpdd.data.QuestionRepositoryImpl
import com.example.quizpdd.domain.repository.QuestionRepository
import org.koin.dsl.module

val appModule = module {

    single<QuestionRepository> { QuestionRepositoryImpl(get(), get()) }
}

