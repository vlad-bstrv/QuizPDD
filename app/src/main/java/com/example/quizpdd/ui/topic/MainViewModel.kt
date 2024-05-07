package com.example.quizpdd.ui.topic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizpdd.domain.model.Topic
import com.example.quizpdd.domain.State
import com.example.quizpdd.domain.repository.QuestionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: QuestionRepository
) : ViewModel() {

    private val _myFlow: MutableStateFlow<State<List<Topic>>> = MutableStateFlow(State.Loading)
    val myFlow: StateFlow<State<List<Topic>>> = _myFlow

    init {
        viewModelScope.launch {
            repository.fetchTopics()
                .collect {
                    _myFlow.value = State.Success(it)
                }
        }
    }

}