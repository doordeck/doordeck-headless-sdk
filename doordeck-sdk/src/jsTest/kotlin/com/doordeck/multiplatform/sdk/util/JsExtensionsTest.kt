package com.doordeck.multiplatform.sdk.util

import com.doordeck.multiplatform.sdk.exceptions.SdkException
import com.doordeck.multiplatform.sdk.size
import kotlinx.coroutines.await
import kotlinx.coroutines.test.runTest
import kotlin.js.collections.JsArray
import kotlin.js.collections.JsMap
import kotlin.js.collections.JsSet
import kotlin.js.collections.toList
import kotlin.js.collections.toMap
import kotlin.js.collections.toMutableMap
import kotlin.js.collections.toSet
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class JsExtensionsTest {

    @Test
    fun shouldCreateEmptyJsArray() = runTest {
        // When
        val empty = emptyJsArray<Int>()

        // Then
        assertEquals(0, empty.size())
        assertIs<JsArray<Int>>(empty)
    }

    @Test
    fun shouldCreateEmptyJsMap() = runTest {
        // When
        val empty = emptyJsMap<Int, Int>()

        // Then
        assertEquals(0, empty.toMutableMap().size)
        assertIs<JsMap<Int, Int>>(empty)
    }

    @Test
    fun shouldMapListToJsArray() = runTest {
        // Given
        val list = listOf(1, 2, 3)

        // When
        val result = list.toJsArray()

        // Then
        assertIs<JsArray<Int>>(result)
        assertEquals(list.size, result.size())
        assertEquals(list, result.toList())
    }

    @Test
    fun shouldMapSetToJsSet() = runTest {
        // Given
        val set = setOf(1, 2, 3)

        // When
        val result = set.toJsSet()

        // Then
        assertIs<JsSet<Int>>(result)
        assertEquals(set.size, result.toSet().size)
        assertEquals(set, result.toSet())
    }

    @Test
    fun shouldMapPairToJsMap() = runTest {
        // Given
        val pairs = listOf(1 to "one", 2 to "two", 3 to "three")

        // When
        val result = pairs.toJsMap()

        // Then
        assertIs<JsMap<Int, String>>(result)
        assertEquals(pairs.size, result.toMutableMap().size)
        assertEquals(pairs, result.toMap().map { it.key to it.value }.toList())
    }

    @Test
    fun shouldCreatePromise() = runTest {
        // Given
        val toReturn = "result"

        // When
        val result = promise {
            toReturn
        }.await()

        // Then
        assertEquals(toReturn, result)
    }

    @Test
    fun shouldFailToProcessPromise() = runTest {
        // Given
        val exception = SdkException("Error")

        // When
        val result = promise {
            exception
        }.await()

        // Then
        assertIs<SdkException>(result)
    }
}