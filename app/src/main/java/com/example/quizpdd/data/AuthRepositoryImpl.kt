package com.example.quizpdd.data

import com.example.quizpdd.domain.common.BaseResult
import com.example.quizpdd.domain.model.User
import com.example.quizpdd.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AuthRepositoryImpl(
    private val api: QuizApi
): AuthRepository  {
    override fun register(user: User): Flow<BaseResult<Boolean, Int>> {
        val apiRequest = flow {
            val response = api.register(user)
            if (response.isSuccessful) {
                emit(BaseResult.Success(true))
            } else {
                emit(BaseResult.Error(response.code()))
            }
        }
        return apiRequest
    }

    override fun auth(user: User): Flow<BaseResult<Boolean, Int>> {
        return flow {
            val response = api.auth(user)
            if (response.isSuccessful) {
                emit(BaseResult.Success(true))
            } else {
                emit(BaseResult.Error(response.code()))
            }
        }
    }


}