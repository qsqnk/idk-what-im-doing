package domain.model

data class Message(
    val id: Long,
    val chatId: Long,
    val sender: String,
    val content: String,
)
