package com.example.my_app_android.example_hilt_mvvm.data.local

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private const val PREFERENCES_NAME = "user_prefs"

// Extension property for DataStore
val Context.dataStore by preferencesDataStore(name = PREFERENCES_NAME)

@Singleton
class HiltMVVMDataStore @Inject constructor(
    private val context: Context
) {
    companion object {
        val DARK_MODE_KEY = booleanPreferencesKey("dark_mode")
    }

    val isDarkMode: Flow<Boolean> = context.dataStore.data
        .map { prefs ->
            prefs[DARK_MODE_KEY] ?: false
        }

    suspend fun saveDarkMode(enabled: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[DARK_MODE_KEY] = enabled
        }
    }
}