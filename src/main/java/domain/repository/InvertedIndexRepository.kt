package domain.repository

import domain.model.InvertedIndexEntry

interface InvertedIndexRepository {
    fun save(entries: List<InvertedIndexEntry>)

    fun getMessageIds(word: String): List<Long>
}