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

// Create the DataStore instance at the top level of your Kotlin file.
// The name "user_preferences" will be the file name where data is stored.
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

class UserPreferences(private val context: Context) {

    // A companion object to hold the key for our boolean flag.
    // This makes it safe to access the key from anywhere.
    companion object {
        // Key for login status
        val IS_LOGGED_IN_KEY = booleanPreferencesKey("is_user_logged_in")

        // Keys for user personal information
        val FIRST_NAME_KEY = stringPreferencesKey("first_name")
        val LAST_NAME_KEY = stringPreferencesKey("last_name")
        val EMAIL_KEY = stringPreferencesKey("email")
    }

    // A Flow to read the login state.
    // Flow allows us to observe changes to the data automatically.
    // This will emit a new value whenever the login state changes.
    val isLoggedIn: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            // If the key doesn't exist, default to 'false' (user is not logged in).
            preferences[IS_LOGGED_IN_KEY] ?: false
        }

    // --- NEW: Flows to read user data ---
    val firstName: Flow<String?> = context.dataStore.data.map { it[FIRST_NAME_KEY] }
    val lastName: Flow<String?> = context.dataStore.data.map { it[LAST_NAME_KEY] }
    val email: Flow<String?> = context.dataStore.data.map { it[EMAIL_KEY] }

    // --- NEW: Function to save all user data at once ---
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