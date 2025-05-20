package com.doordeck.multiplatform.sdk.api

/**
 * Platform-specific implementations of accountless-related API calls.
 */
expect object AccountlessApi

/**
 * Defines the platform-specific implementation of [AccountlessApi]
 */
expect fun accountless(): AccountlessApi