package com.martin.simpleimagecaching.feature_entries.data.data_source

import androidx.room.*
import com.martin.simpleimagecaching.feature_entries.domain.model.Entry
import kotlinx.coroutines.flow.Flow

@Dao
interface EntryDao {

    // use to read all data from the database and its not suspend function because it returns a flow
    @Query("SELECT * FROM entry_table ")
    fun getEntries() : Flow<List<Entry>>


    // used on conflict replace to avoid duplicates and to avoid need to update the database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addEntry(entry: Entry)

    // used to get entry by id
    @Query("SELECT * FROM entry_table WHERE id = :id")
    suspend fun getEntryById(id: Int) : Entry?

    // to delete a single entry
    @Delete()
    suspend fun deleteEntry(entry: Entry)

    // used to delete all entries from database
    @Query("DELETE FROM entry_table")
    suspend fun deleteAllEntry()




}