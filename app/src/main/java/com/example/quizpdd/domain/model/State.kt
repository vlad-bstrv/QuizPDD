package com.example.quizpdd.domain.model

sealed class State<out T> {
    data object Loading : State<Nothing>()
    data class Error(val throwable: Throwable) : State<Nothing>()
    data class Success<T>(val data: T) : State<T>()
}