package com.example.quizpdd.data.remote.model

import com.google.gson.annotations.SerializedName

data class TopicResponse(
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("progress")
    var progress: Int? = null,
    @SerializedName("topic")
    var topics: TopicDTO? = TopicDTO(),
    @SerializedName("topic_id")
    var topicId: Int
)

data class TopicDTO(
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("question")
    var question: ArrayList<QuestionDTO> = arrayListOf()

)

data class QuestionDTO(
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("title")
    var title: String? = null
)

fun TopicResponse.toDomain(): com.example.quizpdd.domain.model.Topic {
    return com.example.quizpdd.domain.model.Topic(
        id = this.topicId ?: 0,
        title = this.topics?.name ?: "",
        progress = this.progress ?: 0,
        allQuestion = this.topics?.question?.size ?: 0
    )
}
