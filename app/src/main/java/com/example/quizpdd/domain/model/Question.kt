package com.example.quizpdd.domain.model

data class Question(
    val id: Int,
    val title: String,
    val question: String,
    val imageUrl: String?,
    val answers: List<Answer>
)


