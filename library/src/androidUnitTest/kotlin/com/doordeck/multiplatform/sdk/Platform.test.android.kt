package com.doordeck.multiplatform.sdk

actual fun getEnvironmentVariable(name: String): String? =
    System.getenv(name)