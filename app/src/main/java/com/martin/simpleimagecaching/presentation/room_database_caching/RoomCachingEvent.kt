package com.martin.simpleimagecaching.presentation.room_database_caching

import android.graphics.Bitmap
import com.martin.simpleimagecaching.feature_entries.domain.model.Entry

sealed class RoomCachingEvent {
    data class AddImage(val imageBitmap: Bitmap) : RoomCachingEvent()
    data class DeleteEntry(val entry: Entry) : RoomCachingEvent()
}
