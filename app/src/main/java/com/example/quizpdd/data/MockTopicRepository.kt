package com.example.quizpdd.data

import com.example.quizpdd.domain.model.Topic
import com.example.quizpdd.domain.repository.TopicRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MockTopicRepository: TopicRepository {
    override fun fetchTopics(): Flow<List<Topic>> = flow {
        delay(1000L)
        emit(loadData())
    }

    private fun loadData() = mutableListOf(
        Topic(1, "Общие положения", 1, 25),
        Topic(2, "Безопасность движения и техника управления автомобилем", 34, 59),
        Topic(3, "Буксировка механических транспортных средств", 2, 8),
        Topic(4, "Движение в жилых зонах", 7, 7),
        Topic(5, "Движение по автомагистралям", 1, 13),
        Topic(6, "Движение через железнодорожные пути", 11, 11),
        Topic(7, "Дорожная разметка", 13, 40),
        Topic(8, "Дорожные знаки", 32, 126),
        Topic(9, "Начало движения, маневрирование", 72, 113),
    )
}