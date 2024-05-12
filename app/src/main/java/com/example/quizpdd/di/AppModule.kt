package com.example.quizpdd.di

import com.example.quizpdd.data.MockQuestionRepository
import com.example.quizpdd.domain.repository.QuestionRepository
import org.koin.dsl.module

val appModule = module {

    single<QuestionRepository> { MockQuestionRepository() }
}

