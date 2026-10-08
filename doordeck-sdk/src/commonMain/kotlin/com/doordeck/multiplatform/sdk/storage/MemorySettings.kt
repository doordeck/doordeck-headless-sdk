package com.doordeck.multiplatform.sdk.storage

import com.russhwolf.settings.Settings
import io.ktor.utils.io.locks.SynchronizedObject
import io.ktor.utils.io.locks.synchronized

/**
 * Thread-safe in-memory storage implementation
 */
internal class MemorySettings(
    private val delegate: MutableMap<String, Any> = mutableMapOf()
) : Settings {

    private val lock = SynchronizedObject()

    override val keys: Set<String> get() = synchronized(lock) { delegate.keys.toSet() }
    override val size: Int get() = synchronized(lock) { delegate.size }

    override fun clear() = synchronized(lock) {
        delegate.clear()
    }

    override fun getBoolean(key: String, defaultValue: Boolean): Boolean = synchronized(lock) {
        delegate[key] as? Boolean ?: defaultValue
    }

    override fun getBooleanOrNull(key: String): Boolean? = synchronized(lock) {
        delegate[key] as? Boolean
    }

    override fun getDouble(key: String, defaultValue: Double): Double = synchronized(lock) {
        delegate[key] as? Double ?: defaultValue
    }

    override fun getDoubleOrNull(key: String): Double? = synchronized(lock) {
        delegate[key] as? Double
    }

    override fun getFloat(key: String, defaultValue: Float): Float = synchronized(lock) {
        delegate[key] as? Float ?: defaultValue
    }

    override fun getFloatOrNull(key: String): Float? = synchronized(lock) {
        delegate[key] as? Float
    }

    override fun getInt(key: String, defaultValue: Int): Int = synchronized(lock) {
        delegate[key] as? Int ?: defaultValue
    }

    override fun getIntOrNull(key: String): Int? = synchronized(lock) {
        delegate[key] as? Int
    }

    override fun getLong(key: String, defaultValue: Long): Long = synchronized(lock) {
        delegate[key] as? Long ?: defaultValue
    }

    override fun getLongOrNull(key: String): Long? = synchronized(lock) {
        delegate[key] as? Long
    }

    override fun getString(key: String, defaultValue: String): String = synchronized(lock) {
        delegate[key] as? String ?: defaultValue
    }

    override fun getStringOrNull(key: String): String? = synchronized(lock) {
        delegate[key] as? String
    }

    override fun hasKey(key: String): Boolean = synchronized(lock) {
        key in delegate
    }

    override fun putBoolean(key: String, value: Boolean) = synchronized(lock) {
        delegate[key] = value
    }

    override fun putDouble(key: String, value: Double) = synchronized(lock) {
        delegate[key] = value
    }

    override fun putFloat(key: String, value: Float) = synchronized(lock) {
        delegate[key] = value
    }

    override fun putInt(key: String, value: Int) = synchronized(lock) {
        delegate[key] = value
    }

    override fun putLong(key: String, value: Long) = synchronized(lock) {
        delegate[key] = value
    }

    override fun putString(key: String, value: String) = synchronized(lock) {
        delegate[key] = value
    }

    override fun remove(key: String) = synchronized(lock) {
        delegate -= key
    }
}