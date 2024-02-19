package com.mykolapetryk.easydatastore

import kotlin.reflect.full.memberProperties

abstract class DataStoreValues {
    internal val dataStoreName = this::class.simpleName ?: "unknown"

    internal fun initDataStoreName(name: String) {
        val fields = this.javaClass.kotlin.memberProperties.map { it.get(this) }
            .filterIsInstance<DataStoreValue<*>>()
        fields.forEach { it.init(name) }
    }

    internal fun toList(): List<DataStoreValue<*>> {
        val fields = this.javaClass.kotlin.memberProperties.map { it.get(this) }.toList()
        return fields.filterIsInstance<DataStoreValue<*>>()
    }
}

internal class SettingsValues(
    val settings: InternalDataStore,
    storeObject: DataStoreValues
) {
    val fields: Map<String, SettingsData<*>>

    init {
        fields = storeObject.toList().associate { it.key to it.toSettingsData(settings) }
    }
}

data class DataStoreValue<T: Any>(
    internal val default: T,
    internal val key: String
) {
    internal lateinit var dataStoreName: String

    internal fun init(name: String) {
        dataStoreName = name
    }

    internal fun toSettingsData(settings: InternalDataStore): SettingsData<*> = when (default) {
        is Boolean -> BooleanSettingsData(
            settings = settings,
            key = key,
            default = default
        )

        is Int -> IntSettingsData(
            settings = settings,
            key = key,
            default = default
        )

        is Float -> FloatSettingsData(
            settings = settings,
            key = key,
            default = default
        )

        is Double -> DoubleSettingsData(
            settings = settings,
            key = key,
            default = default
        )

        is String -> StringSettingsData(
            settings = settings,
            key = key,
            default = default as String
        )

        is Long -> LongSettingsData(
            settings = settings,
            key = key,
            default = default as Long
        )

        is Set<*> -> StringSetSettingsData(
            settings = settings,
            key = key,
            default = default.toStringSet()
        )

        else -> throw InvalidValueTypeException(
            "[$key@$dataStoreName] Cannot save value type of ${default::class.simpleName}"
        )
    }
}

// A custom exception class that indicates an invalid value type for the settings
private class InvalidValueTypeException (message: String) : Exception ("\n\n$message\n")
