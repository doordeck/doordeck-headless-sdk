package com.doordeck.multiplatform.sdk.model.data

import kotlinx.serialization.Serializable

@Serializable
internal data class ResultData<T>(
    val success: SuccessResultData<T>? = null,
    val failure: FailedResultData? = null
)

@Serializable
internal data class SuccessResultData<T>(
    val result: T? = null
)

@Serializable
internal data class FailedResultData(
    val exceptionType: String,
    val exceptionMessage: String
)