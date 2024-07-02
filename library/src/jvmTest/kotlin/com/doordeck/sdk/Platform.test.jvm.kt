package com.doordeck.sdk

actual fun getEnvironmentVariable(name: String): String? = System.getenv(name)