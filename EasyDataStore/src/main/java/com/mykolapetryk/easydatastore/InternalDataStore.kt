package com.mykolapetryk.easydatastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class InternalDataStore(
    private val context: Context,
    private val dataStore: String
) {
    internal suspend fun updateValue(data: SettingsData<*>, value: Any) {
        DataStoreManager.get(context, dataStore).edit { preferences ->
            when (data) {
                is BooleanSettingsData -> preferences[booleanPreferencesKey(data.key)] =
                    value as Boolean

                is IntSettingsData -> preferences[intPreferencesKey(data.key)] = value as Int
                is FloatSettingsData -> preferences[floatPreferencesKey(data.key)] = value as Float
                is StringSettingsData -> preferences[stringPreferencesKey(data.key)] =
                    value as String

                is LongSettingsData -> preferences[longPreferencesKey(data.key)] = value as Long
            }
        }
    }

    internal suspend fun toDefaultValue(data: SettingsData<*>): Boolean {
        try {
            DataStoreManager.get(context, dataStore).edit { preferences ->
                when (data) {
                    is BooleanSettingsData ->
                        preferences[booleanPreferencesKey(data.key)] = data.default

                    is IntSettingsData ->
                        preferences[intPreferencesKey(data.key)] = data.default

                    is FloatSettingsData ->
                        preferences[floatPreferencesKey(data.key)] = data.default

                    is StringSettingsData ->
                        preferences[stringPreferencesKey(data.key)] = data.default

                    is LongSettingsData ->
                        preferences[longPreferencesKey(data.key)] = data.default
                }
            }
            return true
        } catch (e: Exception) {
            return false
        }
    }

    internal suspend fun readValue(data: SettingsData<*>): Any =
        DataStoreManager.get(context, dataStore).data.map { preferences ->
            when (data) {
                is BooleanSettingsData -> preferences[booleanPreferencesKey(data.key)]
                is IntSettingsData -> preferences[intPreferencesKey(data.key)]
                is FloatSettingsData -> preferences[floatPreferencesKey(data.key)]
                is StringSettingsData -> preferences[stringPreferencesKey(data.key)]
                is LongSettingsData -> preferences[longPreferencesKey(data.key)]
            }
        }.first() ?: data.default

    internal fun readAsFlow(data: SettingsData<*>): Flow<Any> =
        DataStoreManager.get(context, dataStore).data.map { preferences ->
            when (data) {
                is BooleanSettingsData -> preferences[booleanPreferencesKey(data.key)]
                is IntSettingsData -> preferences[intPreferencesKey(data.key)]
                is FloatSettingsData -> preferences[floatPreferencesKey(data.key)]
                is StringSettingsData -> preferences[stringPreferencesKey(data.key)]
                is LongSettingsData -> longPreferencesKey(data.key)
            } ?: data.default
        }

    internal suspend fun resetValueType(data: SettingsData<*>) {
        DataStoreManager.get(context, dataStore).edit { preferences ->
            val key = when (data) {
                is BooleanSettingsData -> booleanPreferencesKey(data.key)
                is IntSettingsData -> intPreferencesKey(data.key)
                is FloatSettingsData -> floatPreferencesKey(data.key)
                is StringSettingsData -> stringPreferencesKey(data.key)
                is LongSettingsData -> longPreferencesKey(data.key)
            }

            preferences.remove(key)
        }
    }

    internal suspend fun resetSettings(settings: List<SettingsData<*>>) {
        if (settings.isEmpty()) return
        settings.forEach { data -> toDefaultValue(data) }
    }
}