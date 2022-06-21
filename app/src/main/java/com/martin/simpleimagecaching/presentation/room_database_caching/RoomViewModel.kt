package com.martin.simpleimagecaching.presentation.room_database_caching

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martin.simpleimagecaching.feature_entries.domain.model.Entry
import com.martin.simpleimagecaching.feature_entries.domain.repository.EntryRepository
import com.martin.simpleimagecaching.feature_entries.domain.use_cases.room_usecases.RoomUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoomViewModel @Inject constructor(
    private val repository: EntryRepository,
    private val roomUseCases: RoomUseCases
) : ViewModel() {

    val readEntries: Flow<List<Entry>> = repository.getEntries()



    fun onEvent(event: RoomCachingEvent) {
        when (event) {
            is RoomCachingEvent.AddImage -> {
                viewModelScope.launch(Dispatchers.IO) {
                    repository.addEntry(roomUseCases(event.imageBitmap))
                }
            }
            is RoomCachingEvent.DeleteEntry -> {
                viewModelScope.launch {
                    repository.deleteEntry(event.entry)
                }
            }
        }
    }


}