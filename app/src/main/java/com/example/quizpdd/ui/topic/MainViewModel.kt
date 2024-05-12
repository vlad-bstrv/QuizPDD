package com.example.quizpdd.ui.topic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizpdd.domain.model.Topic
import com.example.quizpdd.domain.common.BaseResult
import com.example.quizpdd.domain.repository.TopicRepository
import com.example.quizpdd.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: TopicRepository
) : ViewModel() {

    private val _state: MutableStateFlow<UiState<List<Topic>>> = MutableStateFlow(
        UiState.IsLoading(false)
    )
    val state: StateFlow<UiState<List<Topic>>> get() = _state

    private fun setLoading(){
        _state.value = UiState.IsLoading(true)
    }

    private fun hideLoading(){
        _state.value = UiState.IsLoading(false)
    }

    private fun success(data: List<Topic>){
        _state.value = UiState.Success(data)
    }

    private fun failed(message: String){
        _state.value = UiState.Error(message)
    }

    fun fetchTopics() {
        viewModelScope.launch{
            repository.fetchTopics()
                .onStart {
                    setLoading()
                }
                .catch {
                    failed(it.message ?: "")
                }
                .collect{
                    hideLoading()
                    when(it) {
                        is BaseResult.Success -> success(it.data)
                        is BaseResult.Error -> failed(it.error)
                    }
                }
        }
    }
}