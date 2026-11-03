package com.example.mytodoapp.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton
import dagger.hilt.android.qualifiers.ApplicationContext

private val Context.dataStore by preferencesDataStore(
    name = "user_preferences"
)

@Singleton
class UserPreferences @Inject constructor(
    @param:ApplicationContext private val context: Context
)

{

    companion object {
        private val SEARCH_QUERY = stringPreferencesKey("search_query")
    }


    suspend fun saveSearchQuery(query: String) {
        context.dataStore.edit { preferences ->
            preferences[SEARCH_QUERY] = query
        }
    }


    fun getSearchQuery(): Flow<String> {
        return context.dataStore.data.map { preferences ->
            preferences[SEARCH_QUERY] ?: ""
        }
    }
}
