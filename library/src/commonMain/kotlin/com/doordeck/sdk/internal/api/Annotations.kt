package com.doordeck.sdk.internal.api

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.BINARY)
@RequiresOptIn(message = "This library API is only available to users with Doordeck issued auth tokens.")
annotation class DoordeckOnly

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.BINARY)
@RequiresOptIn(message = "This library API is only available to users that are lock administrators.")
annotation class SiteAdmin