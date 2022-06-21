package com.martin.simpleimagecaching.feature_entries.data.data_source

import android.content.Context
import androidx.room.*
import com.martin.simpleimagecaching.feature_entries.domain.model.Converters
import com.martin.simpleimagecaching.feature_entries.domain.model.Entry

@Database(entities = [Entry::class] , version = 1 , exportSchema = false)
@TypeConverters(Converters::class)
abstract class EntryDatabase : RoomDatabase() {

    abstract fun entryDao() : EntryDao

    companion object {
        @Volatile
        private var INSTANCE : EntryDatabase? = null

        fun getDatabase(context : Context) : EntryDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EntryDatabase::class.java,
                    "main_database"

                )
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}