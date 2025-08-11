package com.doordeck.multiplatform.sdk.util

import com.doordeck.multiplatform.sdk.Constants.CERTIFICATE_PINNER_DOMAIN_PATTERN
import com.doordeck.multiplatform.sdk.Constants.TRUSTED_CERTIFICATES
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.okhttp.OkHttpConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.future.future
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import java.net.InetAddress
import java.net.URI
import java.net.URL
import java.time.Duration
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.EnumSet
import java.util.UUID
import java.util.concurrent.CompletableFuture

internal actual fun HttpClientConfig<*>.installCertificatePinner() {
    engine {
        if (this is OkHttpConfig) {
            val certificatePinner = CertificatePinner.Builder()
                .add(CERTIFICATE_PINNER_DOMAIN_PATTERN, *TRUSTED_CERTIFICATES.toTypedArray())
                .build()
            preconfigured = OkHttpClient.Builder()
                .certificatePinner(certificatePinner)
                .build()
        }
    }
}

private val TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm")
private val DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd")

internal fun String.toLocalTime(format: DateTimeFormatter = TIME_FORMAT): LocalTime = LocalTime
    .parse(this, format)

internal fun LocalTime.toLocalTimeString(format: DateTimeFormatter = TIME_FORMAT): String = format
    .format(this)

internal fun String.toLocalDate(format: DateTimeFormatter = DATE_FORMAT): LocalDate = LocalDate
    .parse(this, format)

internal fun LocalDate.toLocalDateString(format: DateTimeFormatter = DATE_FORMAT): String = format
    .format(this)

internal fun Int.secondsToDuration(): Duration = Duration.ofSeconds(toLong())

internal fun String.toUri(): URI = URI.create(this)

internal fun String.toUrl(): URL = toUri().toURL()

internal fun String.toZoneId(): ZoneId = ZoneId.of(this)

internal fun String.toUuid(): UUID = UUID.fromString(this)

internal fun String.toInstant(): Instant {
    val split = split(".")
    return Instant.ofEpochSecond(
        split.first().toLong(),
        split.lastOrNull()?.toLong() ?: 0
    )
}

internal fun Double.toInstant(): Instant = Instant.ofEpochSecond(toLong())

internal fun String.toInetAddress(): InetAddress = InetAddress.getByName(this)

internal inline fun <reified T : Enum<T>> List<T>.toEnumSet(): EnumSet<T> =
    if (isNotEmpty()) EnumSet.copyOf(this) else EnumSet.noneOf(T::class.java)

internal fun now(): Instant = Instant.now().truncatedTo(ChronoUnit.SECONDS)

/**
 * Creates a `CompletableFuture` from a suspendable function.
 *
 * This function executes the given suspend function within a coroutine context
 * and returns a `CompletableFuture` that completes once the suspend function
 * finishes. The coroutine is launched in the `IO` dispatcher.
 *
 * @param T The type of the result produced by the suspend function.
 * @param block The suspend function that will be executed asynchronously.
 *
 * @return A `CompletableFuture` that completes with the result of the suspend function.
 */
internal inline fun <reified T>completableFuture(crossinline block: suspend () -> T): CompletableFuture<T> {
    return GlobalScope.future(Dispatchers.IO) {
        block()
    }
}