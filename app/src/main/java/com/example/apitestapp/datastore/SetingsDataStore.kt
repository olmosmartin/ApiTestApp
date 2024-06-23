package com.example.apitestapp.datastore

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.apitestapp.constantes.Constantes
import com.example.apitestapp.datastore.model.SettingsModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class SetingsDataStore(private val context: Context) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    suspend fun saveVolumen(value: Int) {
        context.dataStore.edit { preferences ->
            preferences[intPreferencesKey(Constantes.VOLUMEN_DS)] = value
        }
    }

    suspend fun saveSwitchValues(key: String, value: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[booleanPreferencesKey(key)] = value
        }
    }

    fun getSettings(): Flow<SettingsModel> {
        return context.dataStore.data.map { preferences ->
            SettingsModel(
                preferences[intPreferencesKey(Constantes.VOLUMEN_DS)] ?: 50,
                preferences[booleanPreferencesKey(Constantes.BLUETOOTH_DS)] ?: false,
                preferences[booleanPreferencesKey(Constantes.VIBRATION_DS)] ?: false,
                preferences[booleanPreferencesKey(Constantes.DARK_MODE_DS)] ?: false,
            )
        }
    }
}