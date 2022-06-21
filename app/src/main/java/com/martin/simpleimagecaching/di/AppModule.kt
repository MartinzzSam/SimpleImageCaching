package com.martin.simpleimagecaching.di

import android.app.Application
import com.martin.simpleimagecaching.feature_entries.data.data_source.EntryDatabase
import com.martin.simpleimagecaching.feature_entries.domain.repository.EntryRepository
import com.martin.simpleimagecaching.feature_entries.data.repository.EntryRepositoryImpl
import com.martin.simpleimagecaching.feature_entries.domain.use_cases.room_usecases.RoomUseCases
import com.martin.simpleimagecaching.feature_entries.domain.use_cases.storage_usecases.StorageUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideEntryDatabase(app: Application) : EntryDatabase {
        return EntryDatabase.getDatabase(app)
    }

    @Provides
    @Singleton
    fun provideEntryRepository(database: EntryDatabase) : EntryRepository {
        return EntryRepositoryImpl(database.entryDao())
    }
    @Provides
    @Singleton
    fun provideStorageUseCases(app: Application) : StorageUseCases {
        return StorageUseCases(app)
    }

    @Provides
    @Singleton
    fun provideRoomUseCases(app: Application) : RoomUseCases {
        return RoomUseCases(app)
    }


}