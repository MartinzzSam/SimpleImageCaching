package com.martin.simpleimagecaching.feature_entries.domain.util

import android.graphics.Bitmap
import android.net.Uri
import java.sql.Timestamp

data class InternalStoragePhoto(
    val name : String,
    val path : String ,
    val bmp: Bitmap?,
)