package com.example.quizpdd.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.quizpdd.R
import com.example.quizpdd.data.datastore.TokenStore
import com.example.quizpdd.data.datastore.UserIDStore
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

//        val userIDStore = UserIDStore(this)
//        val tokenStore = TokenStore(this)
//        runBlocking {
//            userIDStore.deleteUserId()
//            tokenStore.deleteToken()
//            Log.d("TAG", "onCreate: s.dfkjgksjdfbgkjbsdlkfgjvbsdkfjgblskdfjbglksdfbgksdfbgkdfjbgk")
//        }

    }
}