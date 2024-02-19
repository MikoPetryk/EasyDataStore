package com.mykolapetryk.easydatastore

import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

sealed class SettingsData<T : Any>(
    internal open val settings: InternalDataStore,
    internal open val key: String,
    internal open val default: T
) {
    internal val currentValue by lazy { mutableStateOf(default) }

    internal fun softReset() {
        currentValue.value = default
    }

    fun reset() {
        val data = this
        currentValue.value = default
        runBlocking(Dispatchers.IO) { settings.toDefaultValue(data) }
    }

    suspend fun resetAsync() {
        currentValue.value = default
        withContext(Dispatchers.IO) {
            settings.toDefaultValue(this@SettingsData)
        }
    }
}

data class BooleanSettingsData(
    override val settings: InternalDataStore,
    override val key: String,
    override val default: Boolean
) : SettingsData<Boolean>(settings, key, default) {
    fun read() = currentValue.value

    fun readAsFlow(): Flow<Boolean> = settings.readAsFlow(this).mapNotNull { it as? Boolean }

    init {
        val data = this

        runBlocking {
            var read = settings.readValue(data)

            if (read !is Boolean) {
                settings.resetValueType(data)
                read = settings.readValue(data)
            }

            currentValue.value = read as Boolean
        }
    }

    fun updateInverted() {
        val data = this
        currentValue.value = !currentValue.value
        runBlocking(Dispatchers.IO) { settings.updateValue(data, currentValue.value) }
    }

    suspend fun updateInvertedAsync() {
        val data = this
        currentValue.value = !currentValue.value
        settings.updateValue(data, currentValue.value)
    }

    fun update(value: Boolean) {
        val data = this
        currentValue.value = value
        runBlocking(Dispatchers.IO) { settings.updateValue(data, value) }
    }

    suspend fun updateAsync(value: Boolean) {
        currentValue.value = value
        settings.updateValue(this, value)
    }
}

data class IntSettingsData(
    override val settings: InternalDataStore,
    override val key: String,
    override val default: Int
) : SettingsData<Int>(settings, key, default) {
    fun read() = currentValue.value

    fun readAsFlow(): Flow<Int> = settings.readAsFlow(this).mapNotNull { it as? Int }

    init {
        val data = this

        runBlocking {
            var k = settings.readValue(data)

            if (k !is Int) {
                settings.resetValueType(data)
                k = settings.readValue(data)
            }

            currentValue.value = k as Int
        }
    }

    fun update(value: Int) {
        val data = this
        currentValue.value = value
        runBlocking(Dispatchers.IO) { settings.updateValue(data, value) }
    }

    suspend fun updateAsync(value: Int) {
        currentValue.value = value
        settings.updateValue(this, value)
    }
}

data class FloatSettingsData(
    override val settings: InternalDataStore,
    override val key: String,
    override val default: Float
) : SettingsData<Float>(settings, key, default) {
    fun read() = currentValue.value
    fun readAsFlow(): Flow<Float> = settings.readAsFlow(this).mapNotNull { it as? Float }

    init {
        val data = this

        runBlocking {
            var k = settings.readValue(data)

            if (k !is Float) {
                settings.resetValueType(data)
                k = settings.readValue(data)
            }

            currentValue.value = k as Float
        }
    }

    fun update(value: Float) {
        val data = this
        currentValue.value = value
        runBlocking(Dispatchers.IO) { settings.updateValue(data, value) }
    }

    suspend fun updateAsync(value: Float) {
        currentValue.value = value
        settings.updateValue(this, value)
    }
}

data class DoubleSettingsData(
    override val settings: InternalDataStore,
    override val key: String,
    override val default: Double
) : SettingsData<Double>(settings, key, default) {
    fun read() = currentValue.value
    fun readAsFlow(): Flow<Double> = settings.readAsFlow(this).mapNotNull { it as? Double }

    init {
        val data = this

        runBlocking {
            var k = settings.readValue(data)

            if (k !is Double) {
                settings.resetValueType(data)
                k = settings.readValue(data)
            }

            currentValue.value = k as Double
        }
    }

    fun update(value: Double) {
        val data = this
        currentValue.value = value
        runBlocking(Dispatchers.IO) { settings.updateValue(data, value) }
    }

    suspend fun updateAsync(value: Double) {
        currentValue.value = value
        settings.updateValue(this, value)
    }
}

data class StringSettingsData(
    override val settings: InternalDataStore,
    override val key: String,
    override val default: String
) : SettingsData<String>(settings, key, default) {
    fun read() = currentValue.value
    fun readAsFlow(): Flow<String> = settings.readAsFlow(this).mapNotNull { it as? String }

    init {
        val data = this

        runBlocking {
            var k = settings.readValue(data)

            if (k !is String) {
                settings.resetValueType(data)
                k = settings.readValue(data)
            }

            currentValue.value = k as String
        }
    }

    fun update(value: String) {
        val data = this
        currentValue.value = value
        runBlocking(Dispatchers.IO) { settings.updateValue(data, value) }
    }

    suspend fun updateAsync(value: String) {
        currentValue.value = value
        settings.updateValue(this, value)
    }
}

data class LongSettingsData(
    override val settings: InternalDataStore,
    override val key: String,
    override val default: Long
) : SettingsData<Long>(settings, key, default) {
    fun read() = currentValue.value
    fun readAsFlow(): Flow<Long> = settings.readAsFlow(this).mapNotNull { it as? Long }

    init {
        val data = this

        runBlocking {
            var k = settings.readValue(data)

            if (k !is Long) {
                settings.resetValueType(data)
                k = settings.readValue(data)
            }

            currentValue.value = k as Long
        }
    }

    fun update(value: Long) {
        val data = this
        currentValue.value = value
        runBlocking(Dispatchers.IO) { settings.updateValue(data, value) }
    }

    suspend fun updateAsync(value: Long) {
        currentValue.value = value
        settings.updateValue(this, value)
    }
}

data class StringSetSettingsData(
    override val settings: InternalDataStore,
    override val key: String,
    override val default: Set<String>
) : SettingsData<Set<String>>(settings, key, default) {
    fun read() = currentValue.value.toMutableSet()
    fun readAsFlow(): Flow<Set<String>> =
        settings.readAsFlow(this).mapNotNull { it.dataStoreToStringSet() }

    init {
        val data = this

        runBlocking {
            var k = settings.readValue(data)

            if (k !is Set<*>) {
                settings.resetValueType(data)
                k = settings.readValue(data)
            }

            currentValue.value = k.dataStoreToStringSet()
        }
    }

    fun update(value: Set<String>) {
        val data = this
        currentValue.value = value
        runBlocking(Dispatchers.IO) { settings.updateValue(data, value) }
    }

    suspend fun updateAsync(value: Set<String>) {
        currentValue.value = value
        settings.updateValue(this, value)
    }
}