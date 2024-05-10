package com.example.quizpdd.ui.common

import android.util.Patterns

fun String.isEmail(): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(this).matches()
}