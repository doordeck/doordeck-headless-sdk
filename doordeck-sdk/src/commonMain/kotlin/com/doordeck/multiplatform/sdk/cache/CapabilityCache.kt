package com.doordeck.multiplatform.sdk.cache

import com.doordeck.multiplatform.sdk.api.model.CapabilityStatus
import com.doordeck.multiplatform.sdk.api.model.CapabilityType

internal object CapabilityCache {
    private val cache = mutableMapOf<String, Map<CapabilityType, CapabilityStatus>>()

    internal fun isSupported(id: String, type: CapabilityType) = cache[id]?.let {
        it[type] == CapabilityStatus.SUPPORTED
    }

    internal fun put(id: String, capabilities: Map<CapabilityType, CapabilityStatus>) {
        cache[id] = capabilities
    }

    internal fun get(id: String): Map<CapabilityType, CapabilityStatus>? = cache[id]

    internal fun reset() {
        cache.clear()
    }
}