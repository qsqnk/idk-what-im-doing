package domain.model

data class Message(
    val id: Long,
    val sender: String,
    val content: String,
)
