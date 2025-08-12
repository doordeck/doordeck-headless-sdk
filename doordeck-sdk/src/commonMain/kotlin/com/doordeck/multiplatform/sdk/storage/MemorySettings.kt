package com.doordeck.multiplatform.sdk.storage

import com.russhwolf.settings.Settings
import kotlin.jvm.JvmSynthetic

/**
 * In-memory storage implementation
 */
internal class MemorySettings(private val delegate: MutableMap<String, Any> = mutableMapOf()) : Settings {

    @get:JvmSynthetic
    override val keys: Set<String> get() = delegate.keys

    @get:JvmSynthetic
    override val size: Int get() = delegate.size

    @JvmSynthetic
    override fun clear() {
        delegate.clear()
    }

    @JvmSynthetic
    override fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return delegate[key] as? Boolean ?: defaultValue
    }

    @JvmSynthetic
    override fun getBooleanOrNull(key: String): Boolean? {
        return delegate[key] as? Boolean
    }

    @JvmSynthetic
    override fun getDouble(key: String, defaultValue: Double): Double {
        return delegate[key] as? Double ?: defaultValue
    }

    @JvmSynthetic
    override fun getDoubleOrNull(key: String): Double? {
        return delegate[key] as? Double
    }

    @JvmSynthetic
    override fun getFloat(key: String, defaultValue: Float): Float {
        return delegate[key] as? Float ?: defaultValue
    }

    @JvmSynthetic
    override fun getFloatOrNull(key: String): Float? {
        return delegate[key] as? Float
    }

    @JvmSynthetic
    override fun getInt(key: String, defaultValue: Int): Int {
        return delegate[key] as? Int ?: defaultValue
    }

    @JvmSynthetic
    override fun getIntOrNull(key: String): Int? {
        return delegate[key] as? Int
    }

    @JvmSynthetic
    override fun getLong(key: String, defaultValue: Long): Long {
        return delegate[key] as? Long ?: defaultValue
    }

    @JvmSynthetic
    override fun getLongOrNull(key: String): Long? {
        return delegate[key] as? Long
    }

    @JvmSynthetic
    override fun getString(key: String, defaultValue: String): String {
        return delegate[key] as? String ?: defaultValue
    }

    @JvmSynthetic
    override fun getStringOrNull(key: String): String? {
        return delegate[key] as? String
    }

    @JvmSynthetic
    override fun hasKey(key: String): Boolean {
        return key in delegate
    }

    @JvmSynthetic
    override fun putBoolean(key: String, value: Boolean) {
        delegate[key] = value
    }

    @JvmSynthetic
    override fun putDouble(key: String, value: Double) {
        delegate[key] = value
    }

    @JvmSynthetic
    override fun putFloat(key: String, value: Float) {
        delegate[key] = value
    }

    @JvmSynthetic
    override fun putInt(key: String, value: Int) {
        delegate[key] = value
    }

    @JvmSynthetic
    override fun putLong(key: String, value: Long) {
        delegate[key] = value
    }

    @JvmSynthetic
    override fun putString(key: String, value: String) {
        delegate[key] = value
    }

    @JvmSynthetic
    override fun remove(key: String) {
        delegate -= key
    }
}