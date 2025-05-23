package com.doordeck.multiplatform.sdk.api

/**
 * Platform-specific implementations of lock-related API calls.
 */
expect object LockOperationsApi

/**
 * Defines the platform-specific implementation of [LockOperationsApi]
 */
expect fun lockOperations(): LockOperationsApi