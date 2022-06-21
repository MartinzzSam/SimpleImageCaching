package com.martin.simpleimagecaching.feature_entries.domain.use_cases.room_usecases

import android.app.Application
import android.graphics.Bitmap
import android.widget.Toast
import com.martin.simpleimagecaching.feature_entries.domain.model.Entry

class RoomUseCases(private val application: Application) {

    operator fun invoke(imageBitmap: Bitmap): Entry {
        return if (imageBitmap.byteCount > 20000000 /* 2mb */ ) {
            val size: Double = imageBitmap.byteCount / 10000000.000000
            val entry = Entry(
                System.currentTimeMillis().toInt(),
                resizeImage(imageBitmap),
                "Image From Room Database size > 2mb ",
                "Size $size mb"
            )
            entry
        } else {
            val size: Double = imageBitmap.byteCount / 10000000.000000
            val entry = Entry(
                System.currentTimeMillis().toInt(),
                imageBitmap,
                "Image From Room Database size < 2mb",
                "Size $size mb"
            )
            entry
        }

    }

    private fun resizeImage(image: Bitmap): Bitmap {

        val width = image.width
        val height = image.height

        val scaleWidth = width / 5
        val scaleHeight = height / 5

        Toast.makeText(application, "Image is bigger than 2 mb", Toast.LENGTH_SHORT).show()

        return Bitmap.createScaledBitmap(image, scaleWidth, scaleHeight, false)
    }
}