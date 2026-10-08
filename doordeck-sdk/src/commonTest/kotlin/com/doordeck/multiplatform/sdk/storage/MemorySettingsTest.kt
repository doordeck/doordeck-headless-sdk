package com.doordeck.multiplatform.sdk.storage

import com.doordeck.multiplatform.sdk.randomBoolean
import com.doordeck.multiplatform.sdk.randomDouble
import com.doordeck.multiplatform.sdk.randomInt
import com.doordeck.multiplatform.sdk.randomLong
import com.doordeck.multiplatform.sdk.randomString
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class MemorySettingsTest {

    @Test
    fun shouldStoreAndRetrieveAllTypes() {
        // Given
        val settings = MemorySettings()
        val boolean = randomBoolean()
        val double = randomDouble()
        val float = Random.nextFloat()
        val int = randomInt()
        val long = randomLong()
        val string = randomString()

        // When
        settings.putBoolean("boolean", boolean)
        settings.putDouble("double", double)
        settings.putFloat("float", float)
        settings.putInt("int", int)
        settings.putLong("long", long)
        settings.putString("string", string)

        // Then
        assertEquals(boolean, settings.getBooleanOrNull("boolean"))
        assertEquals(boolean, settings.getBoolean("boolean", !boolean))
        assertEquals(double, settings.getDoubleOrNull("double"))
        assertEquals(double, settings.getDouble("double", double + 1))
        assertEquals(float, settings.getFloatOrNull("float"))
        assertEquals(float, settings.getFloat("float", float + 1))
        assertEquals(int, settings.getIntOrNull("int"))
        assertEquals(int, settings.getInt("int", int - 1))
        assertEquals(long, settings.getLongOrNull("long"))
        assertEquals(long, settings.getLong("long", long + 1))
        assertEquals(string, settings.getStringOrNull("string"))
        assertEquals(string, settings.getString("string", string + "-default"))
    }

    @Test
    fun shouldOverrideStoredValues() {
        // Given
        val settings = MemorySettings()
        val value = randomString()
        settings.putString("key", randomString())

        // When
        settings.putString("key", value)

        // Then
        assertEquals(value, settings.getStringOrNull("key"))
        assertEquals(1, settings.size)
    }

    @Test
    fun shouldReturnNullOrDefaultForMissingKeys() {
        // Given
        val settings = MemorySettings()
        val boolean = randomBoolean()
        val double = randomDouble()
        val float = Random.nextFloat()
        val int = randomInt()
        val long = randomLong()
        val string = randomString()

        // Then
        assertNull(settings.getBooleanOrNull("missing"))
        assertNull(settings.getDoubleOrNull("missing"))
        assertNull(settings.getFloatOrNull("missing"))
        assertNull(settings.getIntOrNull("missing"))
        assertNull(settings.getLongOrNull("missing"))
        assertNull(settings.getStringOrNull("missing"))
        assertEquals(boolean, settings.getBoolean("missing", boolean))
        assertEquals(double, settings.getDouble("missing", double))
        assertEquals(float, settings.getFloat("missing", float))
        assertEquals(int, settings.getInt("missing", int))
        assertEquals(long, settings.getLong("missing", long))
        assertEquals(string, settings.getString("missing", string))
    }

    @Test
    fun shouldReturnNullOrDefaultForMismatchingTypes() {
        // Given
        val settings = MemorySettings()
        val int = randomInt()
        val string = randomString()
        settings.putString("string", randomString())
        settings.putInt("int", randomInt())

        // Then
        assertNull(settings.getIntOrNull("string"))
        assertNull(settings.getBooleanOrNull("string"))
        assertNull(settings.getStringOrNull("int"))
        assertNull(settings.getLongOrNull("int"))
        assertEquals(int, settings.getInt("string", int))
        assertEquals(string, settings.getString("int", string))
    }

    @Test
    fun shouldRemoveKeys() {
        // Given
        val settings = MemorySettings()
        val value = randomString()
        settings.putString("removed", randomString())
        settings.putString("kept", value)

        // When
        settings.remove("removed")

        // Then
        assertFalse(settings.hasKey("removed"))
        assertNull(settings.getStringOrNull("removed"))
        assertTrue(settings.hasKey("kept"))
        assertEquals(value, settings.getStringOrNull("kept"))
        assertEquals(setOf("kept"), settings.keys)
    }

    @Test
    fun shouldClearAllKeys() {
        // Given
        val settings = MemorySettings()
        settings.putString("string", randomString())
        settings.putInt("int", randomInt())

        // When
        settings.clear()

        // Then
        assertEquals(0, settings.size)
        assertEquals(emptySet(), settings.keys)
        assertFalse(settings.hasKey("string"))
        assertFalse(settings.hasKey("int"))
    }

    @Test
    fun shouldReturnKeysAndSize() {
        // Given
        val settings = MemorySettings()

        // When
        settings.putString("string", randomString())
        settings.putInt("int", randomInt())
        settings.putBoolean("boolean", randomBoolean())

        // Then
        assertEquals(3, settings.size)
        assertEquals(setOf("string", "int", "boolean"), settings.keys)
        assertTrue(settings.hasKey("string"))
        assertFalse(settings.hasKey("missing"))
    }

    @Test
    fun shouldReturnKeysSnapshot() {
        // Given
        val settings = MemorySettings()
        settings.putString("before", randomString())
        val keys = settings.keys

        // When
        settings.putString("after", randomString())

        // Then
        assertEquals(setOf("before"), keys)
    }
}