package domain.repository.impl

import domain.db.Tables.MESSAGE
import domain.db.tables.records.MessageRecord
import domain.model.Message
import domain.model.MessageCreateRq
import domain.repository.MessageRepository
import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class MessageRepositoryImpl @Autowired constructor(
    private val sql: DSLContext,
) : MessageRepository {
    override fun save(message: MessageCreateRq): Long? {
        return sql.insertInto(MESSAGE)
            .set(toRecord(message))
            .returning(MESSAGE.ID)
            .fetchOne()?.id
    }

    override fun get(sender: String, chatId: Long): List<Message> {
        return sql.selectFrom(MESSAGE)
            .where(MESSAGE.SENDER.eq(sender))
            .and(MESSAGE.CHAT_ID.eq(chatId))
            .fetch(::toModel)
    }

    override fun getAll(): List<Message> {
        return sql.selectFrom(MESSAGE)
            .fetch(::toModel)
    }

    companion object {
        private fun toModel(record: MessageRecord) = Message(
            id = record.id,
            chatId = record.chatId,
            sender = record.sender,
            content = record.content,
            createdTs = record.createdTs,
        )

        private fun toRecord(message: MessageCreateRq) = MessageRecord().apply {
            sender = message.sender
            chatId = message.chatId
            content = message.content
            createdTs = System.currentTimeMillis()
        }
    }
}