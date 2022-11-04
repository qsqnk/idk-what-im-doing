package domain.repository

import domain.model.Message
import domain.model.MessageCreateRq

interface MessageRepository {
    fun save(message: MessageCreateRq): Long?

    fun getBySender(sender: String): List<Message>

    fun getAll(): List<Message>
}