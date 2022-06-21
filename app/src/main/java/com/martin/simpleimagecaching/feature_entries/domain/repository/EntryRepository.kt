package com.martin.simpleimagecaching.feature_entries.domain.repository

import com.martin.simpleimagecaching.feature_entries.domain.model.Entry
import kotlinx.coroutines.flow.Flow


//its a fake version of the repository for the sake of of testing , coz we don't wanna use the real database
interface EntryRepository {
    fun getEntries() : Flow<List<Entry>>

    suspend fun addEntry(entry: Entry)

    suspend fun getEntryById(id: Int) : Entry?

    suspend fun deleteEntry(entry: Entry)

    suspend fun deleteAllEntry()

}