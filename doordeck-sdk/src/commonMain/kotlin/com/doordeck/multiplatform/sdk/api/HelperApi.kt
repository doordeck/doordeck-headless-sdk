package com.doordeck.multiplatform.sdk.api

/**
 * Platform-specific implementations of helper-related API calls.
 */
expect object HelperApi

/**
 * Defines the platform-specific implementation of [HelperApi]
 */
expect fun helper(): HelperApi