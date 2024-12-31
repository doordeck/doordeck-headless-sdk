package com.doordeck.multiplatform.sdk.cache

import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_LOCK_ID
import com.doordeck.multiplatform.sdk.api.model.CapabilityStatus
import com.doordeck.multiplatform.sdk.api.model.CapabilityType
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlin.uuid.Uuid

class CapabilityCacheTest {

    @BeforeTest
    fun setup() {
        CapabilityCache.reset()
    }

    @Test
    fun shouldAddCapabilities() = runTest {
        // Given
        val capabilities = mapOf(CapabilityType.BATCH_SHARING_25 to CapabilityStatus.SUPPORTED)

        // When
        CapabilityCache.put(TEST_MAIN_LOCK_ID, capabilities)

        // Then
        assertEquals(capabilities, CapabilityCache.get(TEST_MAIN_LOCK_ID))
    }

    @Test
    fun shouldCheckSupported() = runTest {
        // Given
        val capabilities = mapOf(CapabilityType.BATCH_SHARING_25 to CapabilityStatus.SUPPORTED)

        // When
        CapabilityCache.put(TEST_MAIN_LOCK_ID, capabilities)

        // Then
        val result = CapabilityCache.isSupported(TEST_MAIN_LOCK_ID, CapabilityType.BATCH_SHARING_25)
        assertNotNull(result)
        assertTrue { result }
    }

    @Test
    fun shouldCheckNotSupported() = runTest {
        // Given
        val capabilities = mapOf(CapabilityType.BATCH_SHARING_25 to CapabilityStatus.UNSUPPORTED)

        // When
        CapabilityCache.put(TEST_MAIN_LOCK_ID, capabilities)

        // Then
        val result = CapabilityCache.isSupported(TEST_MAIN_LOCK_ID, CapabilityType.BATCH_SHARING_25)
        assertNotNull(result)
        assertFalse { result }
    }

    @Test
    fun shouldCheckNotSupportedMissingCapability() = runTest {
        // Given
        val capabilities = emptyMap<CapabilityType, CapabilityStatus>()


        // When
        CapabilityCache.put(TEST_MAIN_LOCK_ID, capabilities)

        // Then
        val result = CapabilityCache.isSupported(TEST_MAIN_LOCK_ID, CapabilityType.BATCH_SHARING_25)
        assertNotNull(result)
        assertFalse { result }
    }

    @Test
    fun shouldCheckNotSupportedMissingDevice() = runTest {
        // Given
        val capabilities = emptyMap<CapabilityType, CapabilityStatus>()

        // When
        CapabilityCache.put(Uuid.random().toString(), capabilities)

        // Then
        assertNull(CapabilityCache.isSupported(TEST_MAIN_LOCK_ID, CapabilityType.BATCH_SHARING_25))
    }
}