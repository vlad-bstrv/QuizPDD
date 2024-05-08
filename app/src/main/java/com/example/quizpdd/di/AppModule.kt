package com.example.quizpdd.di

import com.example.quizpdd.data.MockQuestionRepository
import com.example.quizpdd.data.MockTopicRepository
import com.example.quizpdd.domain.repository.QuestionRepository
import com.example.quizpdd.domain.repository.TopicRepository
import com.example.quizpdd.ui.question.QuestionViewModel
import com.example.quizpdd.ui.topic.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<TopicRepository> { MockTopicRepository() }
    single<QuestionRepository> { MockQuestionRepository() }
    viewModel { MainViewModel(get()) }
    viewModel { QuestionViewModel(get()) }
}