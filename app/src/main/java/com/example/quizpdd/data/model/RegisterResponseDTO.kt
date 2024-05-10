package com.example.quizpdd.data.model

import com.google.gson.annotations.SerializedName

data class RegisterResponseDTO(
    @SerializedName("access_token")
    val token: String
)