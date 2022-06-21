package com.martin.simpleimagecaching.presentation.internal_storage_caching

import android.graphics.Bitmap

sealed class InternalStorageEvent{
    data class AddFile( val filename : String , val bitmap: Bitmap ) : InternalStorageEvent()
    data class DeleteFile(val filePath : String) : InternalStorageEvent()
    object RefreshData : InternalStorageEvent()
}
