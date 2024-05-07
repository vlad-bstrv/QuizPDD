package com.example.quizpdd.domain.repository

import com.example.quizpdd.domain.model.Topic
import kotlinx.coroutines.flow.Flow

interface QuestionRepository {
    fun fetchTopics(): Flow<List<Topic>>
}