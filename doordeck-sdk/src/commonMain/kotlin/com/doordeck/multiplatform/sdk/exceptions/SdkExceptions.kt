package com.doordeck.multiplatform.sdk.exceptions

import kotlin.js.JsExport

/**
 * SDK Exceptions
 */
@JsExport
open class SdkException(override val message: String, exception: Throwable? = null) : Exception(message, exception)

@JsExport
open class HttpException(override val message: String, statusCode: Int, exception: HttpException? = null) : SdkException(message, exception)

@JsExport
class MissingContextFieldException(override val message: String) : SdkException(message)

class MissingAndroidContextException(override val message: String) : SdkException(message)

@JsExport
class BatchShareFailedException(override val message: String, userIds: List<String>) : SdkException(message)
