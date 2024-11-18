package com.energykhata

import android.content.Context
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "settings")

object DataStoreHelper {
    private val READING_COUNT_KEY = intPreferencesKey("reading_count")

    suspend fun saveReadingCount(context: Context, count: Int) {
        context.dataStore.edit { settings ->
            settings[READING_COUNT_KEY] = count
        }
    }

    fun getReadingCount(context: Context): Flow<Int> {
        return context.dataStore.data.map { settings ->
            settings[READING_COUNT_KEY] ?: 1 // Default to 1 if not set
        }
    }
}
