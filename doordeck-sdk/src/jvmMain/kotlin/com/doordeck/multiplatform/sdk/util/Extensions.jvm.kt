package com.doordeck.multiplatform.sdk.util

import com.doordeck.multiplatform.sdk.Constants.CERTIFICATE_PINNER_DOMAIN_PATTERN
import com.doordeck.multiplatform.sdk.Constants.TRUSTED_CERTIFICATES
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.okhttp.OkHttpConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.future.future
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.format.DateTimeFormat
import kotlinx.datetime.format.byUnicodePattern
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import java.net.InetAddress
import java.net.URI
import java.net.URL
import java.time.ZoneId
import java.util.UUID
import java.util.concurrent.CompletableFuture
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.Instant

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

private val TIME_FORMAT = LocalTime.Format { byUnicodePattern("HH:mm") }
private val DATE_FORMAT = LocalDate.Format { byUnicodePattern("yyyy-MM-dd") }

internal fun String.toLocalTime(format: DateTimeFormat<LocalTime> = TIME_FORMAT): LocalTime = LocalTime.parse(this, format)
internal fun LocalTime.toLocalTimeString(format: DateTimeFormat<LocalTime> = TIME_FORMAT): String = format.format(this)

internal fun String.toLocalDate(format: DateTimeFormat<LocalDate> =  DATE_FORMAT): LocalDate = LocalDate.parse(this, format)
internal fun LocalDate.toLocalDateString(format: DateTimeFormat<LocalDate> =  DATE_FORMAT): String = format.format(this)

internal fun Double.toDuration(unit: DurationUnit = DurationUnit.SECONDS): Duration = toDuration(unit)
internal fun Duration.toSeconds(): Int = toInt(DurationUnit.SECONDS)

internal fun String.toUri(): URI = URI.create(this)
internal fun String.toUrl(): URL = toUri().toURL()

internal fun String.toZoneId(): ZoneId = ZoneId.of(this)
internal fun String.toUUID(): UUID = UUID.fromString(this)

internal fun String.toInstant(): Instant = Instant.fromEpochSeconds(toLong())
internal fun Double.toInstant(): Instant = Instant.fromEpochSeconds(toLong())

internal fun String.toInetAddress(): InetAddress = InetAddress.ofLiteral(this)

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