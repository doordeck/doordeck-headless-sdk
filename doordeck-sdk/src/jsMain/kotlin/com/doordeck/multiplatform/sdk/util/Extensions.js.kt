package com.doordeck.multiplatform.sdk.util

import io.ktor.client.HttpClientConfig
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.promise
import kotlin.js.Promise

internal actual fun HttpClientConfig<*>.installCertificatePinner() {
    // Certificate pinner is not supported on the JS engine
}

/**
 * Creates a `Promise` from a suspendable function.
 *
 * This function executes the given suspend function asynchronously within a
 * coroutine and returns a `Promise` that completes when the suspend function
 * finishes execution. The coroutine is launched in the default dispatcher.
 *
 * @param T The type of the result produced by the suspend function.
 * @param block The suspend function that will be executed asynchronously.
 *
 * @return A `Promise` that completes with the result of the suspend function.
 */
internal inline fun <reified T>promise(crossinline block: suspend () -> T): Promise<T> {
    return GlobalScope.promise {
        block()
    }
}