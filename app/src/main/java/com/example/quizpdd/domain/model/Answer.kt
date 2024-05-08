package com.example.quizpdd.domain.model

data class Answer(
    val id: Int,
    val answerText: String,
    val isCorrect: Boolean
)