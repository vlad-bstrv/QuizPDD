package com.example.quizpdd.ui.question

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizpdd.domain.State
import com.example.quizpdd.domain.common.BaseResult
import com.example.quizpdd.domain.model.Question
import com.example.quizpdd.domain.model.Topic
import com.example.quizpdd.domain.repository.QuestionRepository
import com.example.quizpdd.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class QuestionViewModel(
    private val repo: QuestionRepository,
) : ViewModel() {

    private val _state: MutableStateFlow<UiState<Question>> = MutableStateFlow(
        UiState.IsLoading(false)
    )
    val state: StateFlow<UiState<Question>> get() = _state

    private val _completionFlow = MutableStateFlow(false)
    val completionFlow = _completionFlow.asStateFlow()

    private val questions = mutableListOf<Question>()
    private var currentQuestionId = 0
    private var rightAnswers = 0
    private var topicId = 0

    private fun setLoading(){
        _state.value = UiState.IsLoading(true)
    }

    private fun hideLoading(){
        _state.value = UiState.IsLoading(false)
    }

    private fun success(data: Question){
        _state.value = UiState.Success(data)
    }

    private fun failed(message: String){
        _state.value = UiState.Error(message)
    }

    fun getQuestion(topicId: Int) {
        this.topicId = topicId
        viewModelScope.launch {
            repo.fetchQuestion(topicId)
                .onStart {
                    setLoading()
                }
                .catch {
                    failed(it.message ?: "")
                }
                .collect{
                    hideLoading()
                    when(it) {
                        is BaseResult.Error -> error(it.error)
                        is BaseResult.Success -> {
                            Log.d("TAG", "getQuestion: ${it.data}")
                            success(it.data.first())
                            questions.addAll(it.data)
                            currentQuestionId = 0
                            rightAnswers = 0
                        }
                    }
                }
        }
//        _myFlow.value = State.Loading
//        this.topicId = topicId
//        viewModelScope.launch {
//            repo.fetchQuestion(topicId)
//                .collect {
//                    _myFlow.value = State.Success(it[0])
//                    questions.addAll(it)
//                    currentQuestionId = 0
//                    rightAnswers = 0
//                }
//        }
    }

    fun nextQuestion() {
        currentQuestionId += 1
        if (currentQuestionId < questions.size) {
            viewModelScope.launch {
                if (currentQuestionId in questions.indices) _state.value =
                    UiState.Success(questions[currentQuestionId])
            }
        } else {
            viewModelScope.launch {
                repo.saveResult(rightAnswers, topicId)
                _completionFlow.value = true
            }
        }
    }

    fun incrementRightAnswers() {
        rightAnswers += 1
    }

    fun getResult() = rightAnswers
}