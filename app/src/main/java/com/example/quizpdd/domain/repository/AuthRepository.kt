package com.example.quizpdd.domain.repository

import com.example.quizpdd.domain.State
import com.example.quizpdd.domain.common.BaseResult
import com.example.quizpdd.domain.model.User
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody

interface AuthRepository {

    fun register(user: User): Flow<BaseResult<Boolean, Int>>

    fun auth(user: User): Flow<BaseResult<Boolean, Int>>
}