package com.example.quizpdd.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.quizpdd.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TokenStore(private val context: Context) {


    fun getToken(): Flow<String?> {
        return context.dataStore.data.map {
            it[TOKEN_KEY]
        }
    }

    suspend fun saveToken(token: String) {
        context.dataStore.edit {
            it[TOKEN_KEY] = token
        }
    }

    suspend fun deleteToken() {
        context.dataStore.edit {
            it.remove(TOKEN_KEY)
        }
    }

    companion object {
        private val TOKEN_KEY = stringPreferencesKey("jwt_token")
    }
}