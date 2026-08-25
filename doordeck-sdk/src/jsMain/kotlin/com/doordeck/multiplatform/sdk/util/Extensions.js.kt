package com.doordeck.multiplatform.sdk.util

import io.ktor.client.HttpClientConfig
import kotlin.js.collections.JsArray
import kotlin.js.collections.JsMap
import kotlin.js.collections.JsSet

internal actual fun HttpClientConfig<*>.installCertificatePinner() {
    // Certificate pinner is not supported on the JS engine
}

internal inline fun <reified T>emptyJsArray(): JsArray<T> = mutableListOf<T>().asJsArrayView()

internal fun <K, V>emptyJsMap() = mutableMapOf<K, V>().asJsMapView()

internal inline fun <reified T>List<T>.toJsArray(): JsArray<T> = toMutableList().asJsArrayView()

internal inline fun <reified T>Iterable<T>.toJsSet(): JsSet<T> = toMutableSet().asJsSetView()

internal fun <K, V>Iterable<Pair<K, V>>.toJsMap(): JsMap<K, V> = toMap().toMutableMap().asJsMapView()