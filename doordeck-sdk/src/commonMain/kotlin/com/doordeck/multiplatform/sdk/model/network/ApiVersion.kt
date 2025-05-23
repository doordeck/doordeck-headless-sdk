package com.doordeck.multiplatform.sdk.model.network

/**
 * Enum representing available API versions for the Doordeck cloud service.
 *
 * Used to specify which API version should be used for network requests.
 */
internal enum class ApiVersion(val version: Int) {
    VERSION_2(2),
    VERSION_3(3)
}