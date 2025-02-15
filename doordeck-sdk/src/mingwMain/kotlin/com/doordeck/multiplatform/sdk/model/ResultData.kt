package com.doordeck.multiplatform.sdk.model

import kotlinx.serialization.Serializable

@Serializable
data class ResultData<T>(
    val success: SuccessResultData<T>? = null,
    val failure: FailedResultData? = null
)

@Serializable
data class SuccessResultData<T>(
    val result: T? = null
)

@Serializable
data class FailedResultData(
    val exceptionType: String,
    val exceptionMessage: String
)