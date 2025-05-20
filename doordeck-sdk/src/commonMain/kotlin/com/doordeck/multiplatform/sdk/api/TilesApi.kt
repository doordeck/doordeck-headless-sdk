package com.doordeck.multiplatform.sdk.api

/**
 * Platform-specific implementations of tile-related API calls.
 */
expect object TilesApi

/**
 * Defines the platform-specific implementation of [TilesApi]
 */
expect fun tiles(): TilesApi