package com.doordeck.multiplatform.sdk.api

/**
 * Platform-specific implementations of platform-related API calls.
 */
expect object PlatformApi

/**
 * Defines the platform-specific implementation of [PlatformApi]
 */
expect fun platform(): PlatformApi