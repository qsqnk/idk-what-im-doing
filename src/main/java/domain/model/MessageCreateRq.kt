package domain.model

data class MessageCreateRq(
    val content: String,
    val sender: String,
    val chatId: Long,
)
