package com.doordeck.multiplatform.sdk.api.cache

import com.doordeck.multiplatform.sdk.api.model.CapabilityStatus
import com.doordeck.multiplatform.sdk.api.model.CapabilityType

internal object CapabilityCache {
    private val cache = mutableMapOf<String, Map<CapabilityType, CapabilityStatus>>()

    internal fun isSupported(id: String, type: CapabilityType) = cache[id]?.let {
        it[type] == CapabilityStatus.SUPPORTED
    }

    internal fun addCapabilities(id: String, capabilities: Map<CapabilityType, CapabilityStatus>) {
        cache[id] = capabilities
    }

    internal fun reset() {
        cache.clear()
    }
}