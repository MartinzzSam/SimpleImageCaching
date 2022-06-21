package com.martin.simpleimagecaching.feature_entries.data.repository

import com.martin.simpleimagecaching.feature_entries.data.data_source.EntryDao
import com.martin.simpleimagecaching.feature_entries.domain.model.Entry
import com.martin.simpleimagecaching.feature_entries.domain.repository.EntryRepository
import kotlinx.coroutines.flow.Flow

class EntryRepositoryImpl(
    private val dao: EntryDao
) : EntryRepository {
    override fun getEntries(): Flow<List<Entry>> {
        return dao.getEntries()
    }

    override suspend fun addEntry(entry: Entry) {
        return dao.addEntry(entry)
    }

    override suspend fun getEntryById(id: Int): Entry? {
        return dao.getEntryById(id)
    }

    override suspend fun deleteEntry(entry: Entry) {
        return dao.deleteEntry(entry)
    }

    override suspend fun deleteAllEntry() {
        dao.deleteAllEntry()
    }

}