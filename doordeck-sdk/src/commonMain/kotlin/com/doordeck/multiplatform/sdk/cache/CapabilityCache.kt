package com.doordeck.multiplatform.sdk.cache

import com.doordeck.multiplatform.sdk.model.common.CapabilityStatus
import com.doordeck.multiplatform.sdk.model.common.CapabilityType

/**
 * A cache to store and retrieve device capabilities.
 *
 * This object maintains a mapping of device IDs to their respective capabilities.
 * Each capability is represented as a map of `CapabilityType` to `CapabilityStatus`.
 */
internal object CapabilityCache {

    /**
     * Internal cache mapping device IDs to their capability maps.
     */
    private val cache = mutableMapOf<String, Map<CapabilityType, CapabilityStatus>>()

    /**
     * Checks if a specific capability type is supported for a given device ID.
     *
     * @param id The unique identifier of the device.
     * @param type The type of capability to check.
     * @return `true` if the specified capability is supported, `false` otherwise.
     */
    internal fun isSupported(id: String, type: CapabilityType) = cache[id]?.let {
        it[type] == CapabilityStatus.SUPPORTED
    }

    /**
     * Adds or updates the capabilities for a given device ID in the cache.
     *
     * @param id The unique identifier of the device.
     * @param capabilities A map of capability types to their statuses.
     */
    internal fun put(id: String, capabilities: Map<CapabilityType, CapabilityStatus>) {
        cache[id] = capabilities
    }

    /**
     * Retrieves the capabilities for a given device ID.
     *
     * @param id The unique identifier of the device.
     * @return A map of capability types to their statuses, or `null` if the device is not in the cache.
     */
    internal fun get(id: String): Map<CapabilityType, CapabilityStatus>? = cache[id]

    /**
     * Clears all entries in the capability cache.
     */
    internal fun reset() {
        cache.clear()
    }
}