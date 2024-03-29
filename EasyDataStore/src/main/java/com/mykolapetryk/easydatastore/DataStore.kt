package com.mykolapetryk.easydatastore

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

object DataStore {
    private lateinit var values: Map<String, SettingsValues>

    operator fun invoke(key: DataStoreValue<Boolean>): BooleanSettingsData {
        try {
            return values[key.dataStoreName]?.fields?.get(key.key) as BooleanSettingsData
        } catch (e: NullPointerException) {
            throw NullPointerException("DataStore for \"${key.dataStoreName}\" was not initialized before using")
        }
    }

    operator fun invoke(key: DataStoreValue<Int>): IntSettingsData {
        try {
            return values[key.dataStoreName]?.fields?.get(key.key) as IntSettingsData
        } catch (e: NullPointerException) {
            throw NullPointerException("DataStore for \"${key.dataStoreName}\" was not initialized before using")
        }
    }

    operator fun invoke(key: DataStoreValue<Float>): FloatSettingsData {
        try {
            return values[key.dataStoreName]?.fields?.get(key.key) as FloatSettingsData
        } catch (e: NullPointerException) {
            throw NullPointerException("DataStore for \"${key.dataStoreName}\" was not initialized before using")
        }
    }

    operator fun invoke(key: DataStoreValue<Double>): DoubleSettingsData {
        try {
            return values[key.dataStoreName]?.fields?.get(key.key) as DoubleSettingsData
        } catch (e: NullPointerException) {
            throw NullPointerException("DataStore for \"${key.dataStoreName}\" was not initialized before using")
        }
    }

    operator fun invoke(key: DataStoreValue<String>): StringSettingsData {
        try {
            return values[key.dataStoreName]?.fields?.get(key.key) as StringSettingsData
        } catch (e: NullPointerException) {
            throw NullPointerException("DataStore for \"${key.dataStoreName}\" was not initialized before using")
        }
    }

    operator fun invoke(key: DataStoreValue<Set<String>>): StringSetSettingsData {
        try {
            return values[key.dataStoreName]?.fields?.get(key.key) as StringSetSettingsData
        } catch (e: NullPointerException) {
            throw NullPointerException("DataStore for \"${key.dataStoreName}\" was not initialized before using")
        }
    }

    operator fun invoke(key: DataStoreValue<Long>): LongSettingsData {
        try {
            return values[key.dataStoreName]?.fields?.get(key.key) as LongSettingsData
        } catch (e: NullPointerException) {
            throw NullPointerException("DataStore for \"${key.dataStoreName}\" was not initialized before using")
        }
    }

    fun resetAll(savedValues: DataStoreValues) {
        val name = savedValues.dataStoreName
        runBlocking(Dispatchers.IO) {
            values[name]?.fields?.map { it.value }?.let {
                values[name]?.settings?.resetSettings(it)
            }

            values[savedValues.dataStoreName]?.fields?.forEach { it.apply { value.softReset() } }
        }
    }

    suspend fun resetAllAsync(savedValues: DataStoreValues) {
        val name = savedValues.dataStoreName
        values[name]?.fields?.map { it.value }?.let {
            values[name]?.settings?.resetSettings(it)
        }
        values[name]?.fields?.forEach { it.apply { value.softReset() } }
    }

    fun start(
        vararg storeObject: DataStoreValues,
    ) {
        values = storeObject.associate { data ->

            data.initDataStoreName(data.dataStoreName)

            data.dataStoreName to SettingsValues(
                settings = InternalDataStore(EasyDataStore.getContext(), data.dataStoreName),
                storeObject = data
            )
        }
    }
}