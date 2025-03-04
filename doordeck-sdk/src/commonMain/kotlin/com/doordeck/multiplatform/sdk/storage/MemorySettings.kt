package com.doordeck.multiplatform.sdk.storage

import com.russhwolf.settings.Settings

/**
 * Memory storage implementation
 */
internal class MemorySettings(private val delegate: MutableMap<String, Any> = mutableMapOf()) : Settings {

    override val keys: Set<String> get() = delegate.keys
    override val size: Int get() = delegate.size

    override fun clear() {
        delegate.clear()
    }

    override fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return delegate[key] as? Boolean ?: defaultValue
    }

    override fun getBooleanOrNull(key: String): Boolean? {
        return delegate[key] as? Boolean
    }

    override fun getDouble(key: String, defaultValue: Double): Double {
        return delegate[key] as? Double ?: defaultValue
    }

    override fun getDoubleOrNull(key: String): Double? {
        return delegate[key] as? Double
    }

    override fun getFloat(key: String, defaultValue: Float): Float {
        return delegate[key] as? Float ?: defaultValue
    }

    override fun getFloatOrNull(key: String): Float? {
        return delegate[key] as? Float
    }

    override fun getInt(key: String, defaultValue: Int): Int {
        return delegate[key] as? Int ?: defaultValue
    }

    override fun getIntOrNull(key: String): Int? {
        return delegate[key] as? Int
    }

    override fun getLong(key: String, defaultValue: Long): Long {
        return delegate[key] as? Long ?: defaultValue
    }

    override fun getLongOrNull(key: String): Long? {
        return delegate[key] as? Long
    }

    override fun getString(key: String, defaultValue: String): String {
        return delegate[key] as? String ?: defaultValue
    }

    override fun getStringOrNull(key: String): String? {
        return delegate[key] as? String
    }

    override fun hasKey(key: String): Boolean {
        return key in delegate
    }

    override fun putBoolean(key: String, value: Boolean) {
        delegate[key] = value
    }

    override fun putDouble(key: String, value: Double) {
        delegate[key] = value
    }

    override fun putFloat(key: String, value: Float) {
        delegate[key] = value
    }

    override fun putInt(key: String, value: Int) {
        delegate[key] = value
    }

    override fun putLong(key: String, value: Long) {
        delegate[key] = value
    }

    override fun putString(key: String, value: String) {
        delegate[key] = value
    }

    override fun remove(key: String) {
        delegate -= key
    }
}