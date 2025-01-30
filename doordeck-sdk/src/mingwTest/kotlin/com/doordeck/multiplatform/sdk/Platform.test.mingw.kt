package com.doordeck.multiplatform.sdk

import com.doordeck.multiplatform.sdk.api.model.ResultData
import com.doordeck.multiplatform.sdk.api.model.SuccessResultData
import com.doordeck.multiplatform.sdk.util.toJson
import kotlinx.cinterop.toKString
import platform.posix.getenv

actual fun getEnvironmentVariable(name: String): String? {
    return getenv(name)?.toKString()
}

internal inline fun <reified T>T.toResultDataJson(): String = ResultData(SuccessResultData(this)).toJson()