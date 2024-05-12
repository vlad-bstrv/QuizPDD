package com.example.quizpdd.data.remote.model

import com.google.gson.annotations.SerializedName

data class TopicResponse(
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("progress")
    var progress: Int? = null,
    @SerializedName("topic_id")
    var topicId: TopicId? = TopicId()
)

data class TopicId(
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("question")
    var question: ArrayList<Question> = arrayListOf()

)

data class Question(
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("title")
    var title: String? = null
)

fun TopicResponse.toDomain(): com.example.quizpdd.domain.model.Topic {
    return com.example.quizpdd.domain.model.Topic(
        id = this.id ?: 0,
        title = this.topicId?.name ?: "",
        progress = this.progress ?: 0,
        allQuestion = this.topicId?.question?.size ?: 0
    )
}
