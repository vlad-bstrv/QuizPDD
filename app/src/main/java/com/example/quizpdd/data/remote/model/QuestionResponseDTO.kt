package com.example.quizpdd.data.remote.model

import com.example.quizpdd.domain.model.Answer
import com.example.quizpdd.domain.model.Question
import com.google.gson.annotations.SerializedName

data class QuestionResponseDTO (
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("question")
    val question: String,
    @SerializedName("image")
    val image: String?,
    @SerializedName("answer")
    val answer: List<AnswerDTO>
)

data class AnswerDTO(
    @SerializedName("id")
    val id: Int,
    @SerializedName("is_correct")
    val isCorrect: Boolean,
    @SerializedName("answer_text")
    val answerText: String
)

fun QuestionResponseDTO.toDomain() = Question(
    id = this.id,
    title = this.title,
    question = this.question,
    imageUrl = this.image,
    answers = this.answer.map {
        it.toDomain()
    }
)

fun AnswerDTO.toDomain() = Answer(
    id = this.id,
    answerText = this.answerText,
    isCorrect = this.isCorrect
)