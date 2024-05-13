package com.example.quizpdd.di

import com.example.quizpdd.ui.auth.login.LoginViewModel
import com.example.quizpdd.ui.auth.register.RegisterViewModel
import com.example.quizpdd.ui.question.QuestionViewModel
import com.example.quizpdd.ui.topic.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val vmModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { QuestionViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { RegisterViewModel(get()) }
}