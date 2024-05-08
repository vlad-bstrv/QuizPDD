package com.example.quizpdd.ui.question

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizpdd.domain.State
import com.example.quizpdd.domain.model.Question
import com.example.quizpdd.domain.repository.QuestionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class QuestionViewModel(
    private val repo: QuestionRepository,
) : ViewModel() {

    private val _myFlow: MutableStateFlow<State<Question>> = MutableStateFlow(State.Loading)
    val myFlow = _myFlow.asStateFlow()

    private val _completionFlow = MutableStateFlow(false)
    val completionFlow = _completionFlow.asStateFlow()

    private val questions = mutableListOf<Question>()
    private var currentQuestionId = 0
    private var rightAnswers = 0
    private var topicId = 0

    fun getQuestion(topicId: Int) {
        _myFlow.value = State.Loading
        this.topicId = topicId
        viewModelScope.launch {
            repo.fetchQuestion(topicId)
                .collect {
                    _myFlow.value = State.Success(it[0])
                    questions.addAll(it)
                    currentQuestionId = 0
                    rightAnswers = 0
                }
        }
    }

    fun nextQuestion() {
        currentQuestionId += 1
        if (currentQuestionId < questions.size) {
            viewModelScope.launch {
                if (currentQuestionId in questions.indices) _myFlow.value =
                    State.Success(questions[currentQuestionId])
            }
        } else {
            repo.saveResult(rightAnswers, topicId)
            _completionFlow.value = true
        }
    }

    fun incrementRightAnswers() {
        rightAnswers += 1
    }

    fun getResult() = rightAnswers
}