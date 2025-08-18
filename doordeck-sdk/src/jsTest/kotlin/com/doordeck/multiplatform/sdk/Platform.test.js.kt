package com.doordeck.multiplatform.sdk

import com.doordeck.multiplatform.sdk.util.toJsArray
import kotlin.js.collections.JsArray
import kotlin.js.collections.toList

actual fun getEnvironmentVariable(name: String): String? =
    js("process.env[name]").unsafeCast<String?>()

internal inline fun <reified T>JsArray<T>.isEmpty(): Boolean = toList().isEmpty()
internal inline fun <reified T>JsArray<T>.isNotEmpty(): Boolean = !isEmpty()
internal inline fun <reified T>JsArray<T>.first(): T = toList().first()
internal inline fun <reified T>JsArray<T>.contains(element: T): Boolean = toList().contains(element)
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