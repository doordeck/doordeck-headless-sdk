package com.doordeck.multiplatform.sdk.api

/**
 * Platform-specific implementations of account-related API calls.
 */
expect object AccountApi

/**
 * Defines the platform-specific implementation of [AccountApi]
 */
expect fun account(): AccountApi