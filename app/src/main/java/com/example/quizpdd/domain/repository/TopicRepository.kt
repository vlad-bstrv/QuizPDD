package com.example.quizpdd.domain.repository

import com.example.quizpdd.domain.common.BaseResult
import com.example.quizpdd.domain.model.Topic
import kotlinx.coroutines.flow.Flow

interface TopicRepository {
    fun fetchTopics(): Flow<BaseResult<List<Topic>, String>>
}
