package com.example.quizpdd.data.remote.model

import com.google.gson.annotations.SerializedName

data class RegisterResponseDTO(
    @SerializedName("email")
    val email: String
)