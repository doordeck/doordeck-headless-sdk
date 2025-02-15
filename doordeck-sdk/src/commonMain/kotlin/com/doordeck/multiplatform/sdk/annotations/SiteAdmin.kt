package com.doordeck.multiplatform.sdk.annotations

/**
 * Used to mark APIs that are accessible only by site admins.
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.BINARY)
internal annotation class SiteAdmin