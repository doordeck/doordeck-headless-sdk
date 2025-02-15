package com.doordeck.multiplatform.sdk.annotations

/**
 * Used to mark APIs that are only available to users with Doordeck issued auth tokens.
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.BINARY)
internal annotation class DoordeckOnly