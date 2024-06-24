package com.doordeck.sdk

class SdkException(
    override val message: String, exception: Throwable? = null
) : RuntimeException(message, exception)
