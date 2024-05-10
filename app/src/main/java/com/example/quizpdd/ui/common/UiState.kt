package com.example.quizpdd.ui.common

sealed class UiState<out T> {
    data class IsLoading(val isLoading: Boolean) : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}