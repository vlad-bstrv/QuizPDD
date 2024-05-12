package com.example.quizpdd.data

import android.util.Log
import com.example.quizpdd.data.datastore.UserIDStore
import com.example.quizpdd.data.remote.QuizApi
import com.example.quizpdd.data.remote.model.toDomain
import com.example.quizpdd.domain.common.BaseResult
import com.example.quizpdd.domain.model.Topic
import com.example.quizpdd.domain.repository.TopicRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking

class TopicRepositoryImpl(
    private val api: QuizApi,
    private val userIDStore: UserIDStore
):TopicRepository {
    override fun fetchTopics(): Flow<BaseResult<List<Topic>, String>> {
        return flow {
            val userId = runBlocking {
                userIDStore.getUserID().first()
            }
            val response = api.getTopics("eq.$userId")
            if (response.isSuccessful) {
                val body = response.body()!!
                Log.d("TAG", "fetchTopics: ${body}")
                emit(BaseResult.Success(body.map {
                    it.toDomain()
                }))
            } else {
                emit(BaseResult.Error(response.message()))
            }
        }
    }

    private fun getUserId() {

    }

}