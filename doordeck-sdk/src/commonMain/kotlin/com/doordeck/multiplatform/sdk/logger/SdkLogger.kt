package com.doordeck.multiplatform.sdk.logger

import co.touchlab.kermit.Logger
import co.touchlab.kermit.NoTagFormatter
import co.touchlab.kermit.Severity
import co.touchlab.kermit.mutableLoggerConfigInit
import co.touchlab.kermit.platformLogWriter
import kotlin.jvm.JvmSynthetic

private val DEFAULT_LOGGING_SEVERITY = Severity.Info

/**
 * Internal global logger implementation.
 */
internal object SdkLogger : Logger(
    config = mutableLoggerConfigInit(
        platformLogWriter(NoTagFormatter),
        minSeverity = Severity.Assert
    )
) {
    /**
     * Enables or disables debug level.
     *
     * @param enabled true to enable debug logging, false to disable it
     */
    @JvmSynthetic
    internal fun enableDebugLogging(enabled: Boolean) {
        mutableConfig.minSeverity = if (enabled) Severity.Debug else DEFAULT_LOGGING_SEVERITY
    }
}