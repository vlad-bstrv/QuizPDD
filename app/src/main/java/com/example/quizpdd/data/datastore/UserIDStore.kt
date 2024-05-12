package com.example.quizpdd.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.quizpdd.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserIDStore(private val context: Context) {

    fun getUserID(): Flow<String?> {
        return context.dataStore.data.map {
            it[USER_ID]
        }
    }

    suspend fun saveUserId(userId: String) {
        context.dataStore.edit {
            it[USER_ID] = userId
        }
    }

    suspend fun deleteUserId() {
        context.dataStore.edit {
            it.remove(USER_ID)
        }
    }

    companion object {
        private val USER_ID = stringPreferencesKey("user_id")
    }
}