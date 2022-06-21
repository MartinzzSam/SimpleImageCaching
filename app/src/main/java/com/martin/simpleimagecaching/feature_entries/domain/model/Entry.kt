package com.martin.simpleimagecaching.feature_entries.domain.model

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey



@Entity(tableName = "entry_table")
data class Entry(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = 0,
    val image : Bitmap? = null,
    val data1 : String,
    val size : String

) {

}

class InvalidEntryException(message: String): Exception(message)
