package com.example.quizpdd.ui.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizpdd.domain.common.BaseResult
import com.example.quizpdd.domain.model.User
import com.example.quizpdd.domain.repository.AuthRepository
import com.example.quizpdd.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class LoginViewModel(private val repo: AuthRepository):ViewModel() {

    private val _state = MutableStateFlow<UiState<Boolean>>(UiState.IsLoading(false))
    val state: StateFlow<UiState<Boolean>> get() = _state

    private fun setLoading(){
        _state.value = UiState.IsLoading(true)
    }

    private fun hideLoading(){
        _state.value = UiState.IsLoading(false)
    }

    private fun success(data: Boolean){
        _state.value = UiState.Success(data)
    }

    private fun failed(message: String){
        _state.value = UiState.Error(message)
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            repo.auth(User(email, password))
                .onStart {
                    setLoading()
                }
                .catch {
                    failed(it.message ?: "error")
                    hideLoading()
                }
                .collect{
                    hideLoading()
                    when(it) {
                        is BaseResult.Success ->success(it.data)
                        is BaseResult.Error -> failed(it.error.toString())
                    }
                }
        }
    }
}

