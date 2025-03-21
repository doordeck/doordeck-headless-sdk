package com.doordeck.multiplatform.sdk

import com.doordeck.multiplatform.sdk.model.data.ResultData
import com.doordeck.multiplatform.sdk.model.data.SuccessResultData
import com.doordeck.multiplatform.sdk.util.toJson
import kotlinx.cinterop.toKString
import platform.posix.getenv

actual fun getEnvironmentVariable(name: String): String? {
    return getenv(name)?.toKString()
}

internal val UNIT_RESULT_DATA = ResultData(SuccessResultData(null)).toJson()

internal inline fun <reified T>T.toResultDataJson(): String = ResultData(SuccessResultData(this)).toJson()