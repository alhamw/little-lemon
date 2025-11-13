package com.fw.littlelemon

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

class UserPreferences(private val context: Context) {

    companion object {
        val IS_LOGGED_IN_KEY = booleanPreferencesKey("is_user_logged_in")
        val FIRST_NAME_KEY = stringPreferencesKey("first_name")
        val LAST_NAME_KEY = stringPreferencesKey("last_name")
        val EMAIL_KEY = stringPreferencesKey("email")
    }

    val isLoggedIn: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[IS_LOGGED_IN_KEY] ?: false
        }

    val firstName: Flow<String?> = context.dataStore.data.map { it[FIRST_NAME_KEY] }
    val lastName: Flow<String?> = context.dataStore.data.map { it[LAST_NAME_KEY] }
    val email: Flow<String?> = context.dataStore.data.map { it[EMAIL_KEY] }

    suspend fun saveUserData(firstName: String, lastName: String, email: String) {
        context.dataStore.edit { preferences ->
            preferences[IS_LOGGED_IN_KEY] = true // Mark as logged in
            preferences[FIRST_NAME_KEY] = firstName
            preferences[LAST_NAME_KEY] = lastName
            preferences[EMAIL_KEY] = email
        }
    }

    suspend fun clearUserData() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }

}