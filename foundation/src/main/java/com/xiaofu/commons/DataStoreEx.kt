package com.xiaofu.commons

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import com.google.gson.Gson
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

/**
 * DataStore封装
 */
const val TIME_SECONDS = 1000
const val TIME_MINUTE = TIME_SECONDS * 60
const val TIME_HOUR = TIME_MINUTE * 60
const val TIME_DAY = TIME_HOUR * 24

/**
 * @param t 包装
 * @param validity 有效日期截止时间
 */
data class FancyCache<T>(
    val t: T,
    val validity: Long
)

/**
 * @param key
 * @param value 只支持基本类型
 */
suspend fun <T> DataStore<Preferences>.put(key: String, value: T) = edit { dataStore ->
    when (value) {
        is Int -> dataStore[intPreferencesKey(key)] = value as Int
        is Double -> dataStore[doublePreferencesKey(key)] = value as Double
        is String -> dataStore[stringPreferencesKey(key)] = value as String
        is Boolean -> dataStore[booleanPreferencesKey(key)] = value as Boolean
        is Float -> dataStore[floatPreferencesKey(key)] = value as Float
        is Long -> dataStore[longPreferencesKey(key)] = value as Long
        else -> throw IllegalArgumentException("Type not supported")
    }
}

/**
 * @param key
 * @param defaultValue
 */
@Suppress("UNCHECKED_CAST")
suspend fun <T> DataStore<Preferences>.get(key: String, defaultValue: T): T {
    return when (defaultValue) {
        is Int -> data.map { it[intPreferencesKey(key)] ?: defaultValue }
            .catch { emit(defaultValue) }.first() as T
        is Double -> data.map { it[doublePreferencesKey(key)] ?: defaultValue }
            .catch { emit(defaultValue) }.first() as T
        is String -> data.map { it[stringPreferencesKey(key)] ?: defaultValue }
            .catch { emit(defaultValue) }.first() as T
        is Boolean -> data.map { it[booleanPreferencesKey(key)] ?: defaultValue }
            .catch { emit(defaultValue) }.first() as T
        is Float -> data.map { it[floatPreferencesKey(key)] ?: defaultValue }
            .catch { emit(defaultValue) }.first() as T
        is Long -> data.map { it[longPreferencesKey(key)] ?: defaultValue }
            .catch { emit(defaultValue) }.first() as T
        else -> throw IllegalArgumentException("Type not supported")
    }
}

/**
 * 序列化对象，以String形式储存
 * @see [put]
 * @param saveTime 保存时间，单位毫秒
 */
suspend fun <T> DataStore<Preferences>.put(key: String, value: T, saveTime: Int) {
    val mCache = FancyCache(value, System.currentTimeMillis() + saveTime)
    val gson = Gson()
    put(key, gson.toJson(mCache))
}

/**
 * 获取带有效期的储存对象
 * @return 过期会清理数据并返回null
 */
suspend inline fun <reified T> DataStore<Preferences>.getValidity(key: String): T? {
    val objStr = get(key, "")
    val gson = Gson()
    val type = ParameterizedTypeImpl(FancyCache::class.java, arrayOf(T::class.java))
    val curTime = System.currentTimeMillis()
    return try {
        val mCache: FancyCache<T> = gson.fromJson(objStr, type)
        if (curTime > mCache.validity) {
            edit { it.remove(stringPreferencesKey(key)) }
            null
        } else
            mCache.t
    } catch (e: Exception) {
        null
    }
}