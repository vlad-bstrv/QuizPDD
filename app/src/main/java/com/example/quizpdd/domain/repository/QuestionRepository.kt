package com.example.quizpdd.domain.repository

import com.example.quizpdd.domain.model.Question
import kotlinx.coroutines.flow.Flow

interface QuestionRepository {

    fun fetchQuestion(topicId: Int): Flow<List<Question>>

    fun saveResult(rightAnswers: Int, topicId: Int)
}