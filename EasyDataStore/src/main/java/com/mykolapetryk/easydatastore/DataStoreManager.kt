package com.mykolapetryk.easydatastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile

internal object DataStoreManager {
    private var saved: MutableMap<String, DataStore<Preferences>> = mutableMapOf()

    fun get(context: Context, name: String): DataStore<Preferences> = saved.getOrPut(name) {
        PreferenceDataStoreFactory.create { context.preferencesDataStoreFile(name) }
    }
}