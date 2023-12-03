package io.github.lee.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

fun String.intKey(): Preferences.Key<Int> = intPreferencesKey(this)

fun String.doubleKey(): Preferences.Key<Double> = doublePreferencesKey(this)

fun String.stringKey(): Preferences.Key<String> = stringPreferencesKey(this)

fun String.booleanKey(): Preferences.Key<Boolean> = booleanPreferencesKey(this)

fun String.floatKey(): Preferences.Key<Float> = floatPreferencesKey(this)

fun String.longKey(): Preferences.Key<Long> = longPreferencesKey(this)


//==
class DataStorePreferences(private val ds: DataStore<Preferences>) {


    //==


    suspend fun clear() {
        ds.edit { it.clear() }
    }

    suspend fun remove(key: Preferences.Key<*>) {
        ds.edit { it.remove(key) }
    }

    suspend fun <T> write(key: Preferences.Key<T>, value: T?) {
        ds.edit {
            when (value) {
                null -> it.remove(key)
                is Int -> it[key] = value
                is Double -> it[key] = value
                is String -> it[key] = value
                is Boolean -> it[key] = value
                is Float -> it[key] = value
                is Long -> it[key] = value
                is Set<*> -> it[key] = value
                else -> throw IllegalArgumentException("This type cannot be saved to the Data Store")
            }
        }
    }

    suspend fun <T> write(key: String, value: T?) {
        if (null == value) {
            return
        }
        when (value) {
            is Int -> write(key.intKey(), value)
            is Double -> write(key.doubleKey(), value)
            is String -> write(key.stringKey(), value)
            is Boolean -> write(key.booleanKey(), value)
            is Float -> write(key.floatKey(), value)
            is Long -> write(key.longKey(), value)
            else -> throw IllegalArgumentException("This type cannot be saved to the Data Store")
        }
    }


    suspend fun <T> read(key: Preferences.Key<T>, defaultValue: T?): T? {
        return if (null == defaultValue) {
            ds.data.map {
                it[key]
            }.first()
        } else {
            when (defaultValue) {
                is Int -> ds.data.map {
                    it[key] ?: defaultValue
                }.first()

                is Double -> ds.data.map {
                    it[key] ?: defaultValue
                }.first()

                is String -> ds.data.map {
                    it[key] ?: defaultValue
                }.first()

                is Boolean -> ds.data.map {
                    it[key] ?: defaultValue
                }.first()

                is Float -> ds.data.map {
                    it[key] ?: defaultValue
                }.first()

                is Long -> ds.data.map {
                    it[key] ?: defaultValue
                }.first()

                is Set<*> -> ds.data.map {
                    it[key] ?: defaultValue
                }.first()

                else -> throw IllegalArgumentException("This type cannot be saved to the Data Store")
            }
        }

    }

    //    suspend fun <T> read(key: String, defaultValue: T?): T? {
//        val t = when (defaultValue) {
//            is Int -> read(key.intKey(), defaultValue)
//            is Double -> read(key.doubleKey(), defaultValue)
//            is String -> read(key.stringKey(), defaultValue)
//            is Boolean -> read(key.booleanKey(), defaultValue)
//            is Float -> read(key.floatKey(), defaultValue)
//            is Long -> read(key.longKey(), defaultValue)
//
//            else -> throw IllegalArgumentException("This type cannot be saved to the Data Store")
//        }
//        return t?:defaultValue
//    }
    suspend fun count(): Int = ds.data.count()

}