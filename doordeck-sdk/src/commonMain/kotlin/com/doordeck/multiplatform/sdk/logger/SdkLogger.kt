package com.doordeck.multiplatform.sdk.logger

import co.touchlab.kermit.Logger
import co.touchlab.kermit.NoTagFormatter
import co.touchlab.kermit.Severity
import co.touchlab.kermit.loggerConfigInit
import co.touchlab.kermit.platformLogWriter

internal object SdkLogger : Logger(
    config = loggerConfigInit(
        platformLogWriter(NoTagFormatter),
        minSeverity = Severity.Assert
    )
) {
    fun enableDebugLogging(enabled: Boolean) {
        mutableConfig.minSeverity = if (enabled) Severity.Debug else Severity.Assert
    }
}