package com.example.quizpdd.data.remote.model

import com.google.gson.annotations.SerializedName

data class AuthResponseDTO(
    @SerializedName("access_token")
    val token: String,
    @SerializedName("user")
    val userDTO: UserDTO
)
