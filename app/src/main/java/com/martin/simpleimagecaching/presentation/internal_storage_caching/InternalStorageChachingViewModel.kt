package com.martin.simpleimagecaching.presentation.internal_storage_caching

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martin.simpleimagecaching.feature_entries.domain.use_cases.storage_usecases.StorageUseCases
import com.martin.simpleimagecaching.feature_entries.domain.util.InternalStoragePhoto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InternalStorageCachingViewModel @Inject constructor(
    private val storageUseCases: StorageUseCases
) : ViewModel() {

    private val _state = MutableSharedFlow<List<InternalStoragePhoto>>()
    val state : SharedFlow<List<InternalStoragePhoto>> = _state.asSharedFlow()


    init {
        viewModelScope.launch {
            val data = storageUseCases.loadPhotosFromInternalStorage()
            if (!data.all { false }) {
                _state.emit(data)
            }

        }
    }

    fun onEvent(event: InternalStorageEvent) {

        when (event) {
            is InternalStorageEvent.AddFile -> {
                val isUpdated = storageUseCases.savePhotoToInternalStorage(event.filename, event.bitmap)
                if (isUpdated) {
                    viewModelScope.launch(Dispatchers.IO) {
                        _state.emit(storageUseCases.loadPhotosFromInternalStorage())

                    }
                }
            }
            is InternalStorageEvent.DeleteFile -> {
                viewModelScope.launch(Dispatchers.IO) {
                    storageUseCases.deletePhotoFromInternalStorage(event.filePath)

                }
            }
            is InternalStorageEvent.RefreshData -> {
                viewModelScope.launch {
                    val data = storageUseCases.loadPhotosFromInternalStorage()
                    if (!data.all { false }) {
                        _state.emit(data)
                    }
                }
            }
        }

    }



}