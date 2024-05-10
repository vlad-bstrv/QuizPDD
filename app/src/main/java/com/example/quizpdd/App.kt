package com.example.quizpdd

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.quizpdd.di.appModule
import com.example.quizpdd.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "data_store")

class App: Application() {


    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(appModule, networkModule)
        }
    }
}