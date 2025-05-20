package com.doordeck.multiplatform.sdk.api

/**
 * Platform-specific implementations of sites-related API calls.
 */
expect object SitesApi

/**
 * Defines the platform-specific implementation of [SitesApi]
 */
expect fun sites(): SitesApi