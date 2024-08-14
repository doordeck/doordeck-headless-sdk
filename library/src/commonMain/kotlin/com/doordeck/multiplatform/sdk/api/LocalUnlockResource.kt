package com.doordeck.multiplatform.sdk.api

interface LocalUnlockResource {

    fun unlock(directAccessEndpoints: Array<String>, request: String)
}