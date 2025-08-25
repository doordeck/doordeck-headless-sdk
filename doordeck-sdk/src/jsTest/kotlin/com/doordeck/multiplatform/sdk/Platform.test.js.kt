package com.doordeck.multiplatform.sdk

import com.doordeck.multiplatform.sdk.util.toJsArray
import com.doordeck.multiplatform.sdk.util.toJsSet
import kotlin.js.collections.JsArray
import kotlin.js.collections.JsSet
import kotlin.js.collections.toList
import kotlin.js.collections.toSet

actual fun getEnvironmentVariable(name: String): String? =
    js("process.env[name]").unsafeCast<String?>()

internal inline fun <reified T>JsArray<T>.isEmpty(): Boolean = toList().isEmpty()
internal inline fun <reified T>JsArray<T>.isNotEmpty(): Boolean = !isEmpty()
internal inline fun <reified T>JsArray<T>.first(): T = toList().first()
internal inline fun <reified T>JsArray<T>.contains(element: T): Boolean = toList().contains(element)
internal inline fun <reified T>jsSetOf(element: T) = setOf(element).toJsSet()
internal inline fun <reified T>JsSet<T>.first(): T = toSet().first()
internal inline fun <reified T>jsArrayOf(element: T) = listOf(element).toJsArray()
internal inline fun <reified T>jsArrayOf(vararg elements: T) = elements.asList().toJsArray()
internal inline fun <reified T>JsArray<T>.any(predicate: (T) -> Boolean) = toList().any {
    predicate(it)
}
internal inline fun <reified T>JsArray<T>.firstOrNull() = toList().firstOrNull()
internal inline fun <reified T>JsArray<T>.firstOrNull(predicate: (T) -> Boolean) = toList().firstOrNull {
    predicate(it)
}

internal inline fun <reified T>JsArray<T>.size() = toList().size
internal inline fun <reified T>emptyJsSet(): JsSet<T> = emptySet<T>().toMutableSet().asJsSetView()