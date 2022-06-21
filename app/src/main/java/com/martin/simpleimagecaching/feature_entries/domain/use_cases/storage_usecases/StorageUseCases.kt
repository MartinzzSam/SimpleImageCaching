package com.martin.simpleimagecaching.feature_entries.domain.use_cases.storage_usecases

import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import com.martin.simpleimagecaching.feature_entries.domain.util.InternalStoragePhoto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.IOException
import java.nio.file.Path


class StorageUseCases(private val application: Application) {
    suspend fun loadPhotosFromInternalStorage(): List<InternalStoragePhoto> {
        return withContext(Dispatchers.IO) {
            val files =  application.filesDir.listFiles()
            files?.filter { it.canRead() && it.isFile && it.name.endsWith(".jpg") }?.map {
                val bytes = it.readBytes()
                val bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                InternalStoragePhoto(it.name, it.path , bmp)
            } ?: listOf()
        }
    }


    fun savePhotoToInternalStorage(filename: String, bmp: Bitmap): Boolean {
        return try {
           application.openFileOutput("$filename.jpg", AppCompatActivity.MODE_PRIVATE).use { stream ->
                if(!bmp.compress(Bitmap.CompressFormat.JPEG, 95, stream)) {
                    throw IOException("Couldn't save bitmap.")
                }
            }
            true
        } catch(e: IOException) {
            e.printStackTrace()
            false
        }
    }
     fun deletePhotoFromInternalStorage(filePath: String): Boolean {
        return try {
            application.deleteFile(filePath)
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}