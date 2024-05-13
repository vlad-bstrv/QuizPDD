package com.example.quizpdd.data

import com.example.quizpdd.data.datastore.UserIDStore
import com.example.quizpdd.data.remote.QuizApi
import com.example.quizpdd.data.remote.model.ProgressRequest
import com.example.quizpdd.data.remote.model.toDomain
import com.example.quizpdd.domain.common.BaseResult
import com.example.quizpdd.domain.model.Question
import com.example.quizpdd.domain.repository.QuestionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking

class QuestionRepositoryImpl(
    private val api: QuizApi,
    private val userIDStore: UserIDStore
): QuestionRepository {
    override fun fetchQuestion(topicId: Int): Flow<BaseResult<List<Question>, String>> {
        return flow {
            val response = api.getQuestion("eq.$topicId")
            if (response.isSuccessful) {
                val body = response.body()!!
                emit(BaseResult.Success(body.map { it.toDomain() }))
            } else {
                emit(BaseResult.Error(response.message()))
            }
        }
    }

    override suspend fun saveResult(rightAnswers: Int, topicId: Int) {
        val userId = runBlocking {
            userIDStore.getUserID().first()
        }
        api.saveProgress(
            userId = "eq.$userId",
            topicId = "eq.$topicId",
            progress = ProgressRequest(progress = rightAnswers.toString())
        )
    }
}