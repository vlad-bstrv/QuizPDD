package com.example.quizpdd.data.model

import com.google.gson.annotations.SerializedName

data class AuthResponseDTO(
    @SerializedName("email")
    val email: String
)
