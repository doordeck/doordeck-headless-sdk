package com.doordeck.multiplatform.sdk.api

/**
 * Platform-specific implementations of fusion-related API calls.
 */
expect object FusionApi

/**
 * Defines the platform-specific implementation of [FusionApi]
 */
expect fun fusion(): FusionApi