package com.doordeck.multiplatform.sdk.cache

import com.doordeck.multiplatform.sdk.model.common.CapabilityStatus
import com.doordeck.multiplatform.sdk.model.common.CapabilityType
import com.doordeck.multiplatform.sdk.randomUuidString
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class CapabilityCacheTest {

    @BeforeTest
    fun setup() = runTest {
        CapabilityCache.reset()
    }

    @Test
    fun shouldAddCapabilities() = runTest {
        // Given
        val capabilities = mapOf(CapabilityType.BATCH_SHARING_25 to CapabilityStatus.SUPPORTED)
        val lockId = randomUuidString()

        // When
        CapabilityCache.put(lockId, capabilities)

        // Then
        assertEquals(capabilities, CapabilityCache.get(lockId))
    }

    @Test
    fun shouldCheckSupported() = runTest {
        // Given
        val capabilities = mapOf(CapabilityType.BATCH_SHARING_25 to CapabilityStatus.SUPPORTED)
        val lockId = randomUuidString()

        // When
        CapabilityCache.put(lockId, capabilities)

        // Then
        val result = CapabilityCache.isSupported(lockId, CapabilityType.BATCH_SHARING_25)
        assertNotNull(result)
        assertTrue { result }
    }

    @Test
    fun shouldCheckNotSupported() = runTest {
        // Given
        val capabilities = mapOf(CapabilityType.BATCH_SHARING_25 to CapabilityStatus.UNSUPPORTED)
        val lockId = randomUuidString()

        // When
        CapabilityCache.put(lockId, capabilities)

        // Then
        val result = CapabilityCache.isSupported(lockId, CapabilityType.BATCH_SHARING_25)
        assertNotNull(result)
        assertFalse { result }
    }

    @Test
    fun shouldCheckNotSupportedMissingCapability() = runTest {
        // Given
        val capabilities = emptyMap<CapabilityType, CapabilityStatus>()
        val lockId = randomUuidString()

        // When
        CapabilityCache.put(lockId, capabilities)

        // Then
        val result = CapabilityCache.isSupported(lockId, CapabilityType.BATCH_SHARING_25)
        assertNotNull(result)
        assertFalse { result }
    }

    @Test
    fun shouldCheckNotSupportedMissingDevice() = runTest {
        // Given
        val capabilities = emptyMap<CapabilityType, CapabilityStatus>()
        val lockId = randomUuidString()

        // When
        CapabilityCache.put(randomUuidString(), capabilities)

        // Then
        assertNull(CapabilityCache.isSupported(lockId, CapabilityType.BATCH_SHARING_25))
    }
}