package com.example.quizpdd.di

import com.example.quizpdd.data.MockQuestionRepository
import com.example.quizpdd.domain.repository.QuestionRepository
import com.example.quizpdd.ui.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<QuestionRepository> { MockQuestionRepository() }
    viewModel { MainViewModel(get()) }
}