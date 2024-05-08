package com.example.quizpdd.data

import com.example.quizpdd.domain.model.Answer
import com.example.quizpdd.domain.model.Question
import com.example.quizpdd.domain.repository.QuestionRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MockQuestionRepository : QuestionRepository {
    override fun fetchQuestion(topicId: Int): Flow<List<Question>> = flow {
        delay(1000L)
        emit(loadData())
    }

    override fun saveResult(rightAnswers: Int, topicId: Int) {

    }

    private fun loadData() = mutableListOf(
        Question(
            id = 1,
            title = "Вопрос 1",
            question = "Какое удостоверение достаточно иметь водителю, управляющему легковым автомобилем с прицепом, разрешенная максимальная масса которого не превышает 750 кг?",
            imageUrl = null,
            listOf(
                Answer(
                    id = 1,
                    answerText = "На право управления транспортным средством подкатегории «B1»",
                    isCorrect = false
                ),
                Answer(
                    id = 2,
                    answerText = "На право управления транспортным средством категории «В»",
                    isCorrect = true
                ),
                Answer(
                    id = 3,
                    answerText = "На право управления транспортным средством категории «BE»",
                    isCorrect = false
                )
            )
        ),
        Question(
            id = 2,
            title = "Вопрос 2",
            question = "Знаки предупреждают Вас о том, что:",
            imageUrl = "https://github.com/etspring/pdd_russia/blob/master/images/A_B/ccfaa71247b7717e204220f439c65a8a.jpg?raw=true",
            listOf(
                Answer(
                    id = 1,
                    answerText = "На протяжении 150 м возможно появление пешеходов на проезжей части",
                    isCorrect = false
                ),
                Answer(
                    id = 2,
                    answerText = "Через 150 м находится пешеходный переход",
                    isCorrect = true
                ),
                Answer(
                    id = 3,
                    answerText = "Через 150 м находится пешеходная дорожка",
                    isCorrect = false
                )
            )
        ),
        Question(
            id = 3,
            title = "Вопрос 3",
            question = "Вам разрешено продолжить движение:",
            imageUrl = "https://github.com/etspring/pdd_russia/blob/master/images/A_B/7aba71e4395c08dd71f87c07c8ccbab8.jpg?raw=true",
            listOf(
                Answer(
                    id = 1,
                    answerText = "Только в направлении Б",
                    isCorrect = true
                ),
                Answer(
                    id = 2,
                    answerText = "В направлениях А или Б",
                    isCorrect = false
                ),
                Answer(
                    id = 3,
                    answerText = "В направлениях Б или В",
                    isCorrect = false
                )
            )
        ),
        Question(
            id = 17,
            title = "Вопрос 17",
            question = "Водителям мопедов разрешено двигаться:",
            imageUrl = null,
            listOf(
                Answer(
                    id = 1,
                    answerText = "Только по правому краю проезжей части в один ряд",
                    isCorrect = false
                ),
                Answer(
                    id = 2,
                    answerText = "Только по обочине, если не создаются помехи пешеходам",
                    isCorrect = false
                ),
                Answer(
                    id = 3,
                    answerText = "Только по полосе для велосипедистов",
                    isCorrect = false
                ),
                Answer(
                    id = 4,
                    answerText = "Во всех перечисленных случаях",
                    isCorrect = true
                )
            )
        )
    )
}