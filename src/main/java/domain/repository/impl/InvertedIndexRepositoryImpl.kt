package domain.repository.impl

import domain.db.Tables.INVERTED_INDEX
import domain.db.tables.records.InvertedIndexRecord
import domain.model.InvertedIndexEntry
import domain.repository.InvertedIndexRepository
import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class InvertedIndexRepositoryImpl @Autowired constructor(
    private val sql: DSLContext,
) : InvertedIndexRepository {
    override fun save(entries: List<InvertedIndexEntry>) {
        entries
            .map(::toRecord)
            .let(sql::batchInsert)
            .execute()
    }

    override fun getMessageIds(word: String): List<Long> {
        return sql.selectFrom(INVERTED_INDEX)
            .where(INVERTED_INDEX.WORD.eq(word))
            .fetch(::toModel)
            .map { it.messageId }
    }

    companion object {
        private fun toRecord(model: InvertedIndexEntry) = InvertedIndexRecord().apply {
            word = model.word
            messageId = model.messageId
        }

        private fun toModel(record: InvertedIndexRecord) = InvertedIndexEntry(
            word = record.word,
            messageId = record.messageId,
        )
    }
}