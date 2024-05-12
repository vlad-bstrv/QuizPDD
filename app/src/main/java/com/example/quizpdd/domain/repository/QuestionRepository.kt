package com.example.quizpdd.domain.repository

import com.example.quizpdd.domain.common.BaseResult
import com.example.quizpdd.domain.model.Question
import kotlinx.coroutines.flow.Flow

interface QuestionRepository {

    fun fetchQuestion(topicId: Int): Flow<BaseResult<List<Question>, String>>

    suspend fun saveResult(rightAnswers: Int, topicId: Int)
}