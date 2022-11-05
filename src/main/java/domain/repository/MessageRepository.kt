package domain.repository

import domain.model.Message
import domain.model.MessageCreateRq

interface MessageRepository {
    fun save(message: MessageCreateRq): Long?

    fun get(sender: String, chatId: Long): List<Message>

    fun getAll(): List<Message>
}