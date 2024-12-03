package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.api.HelperResource
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.promise
import kotlin.js.Promise

internal class HelperResourceImpl(
    private val helperClient: HelperClient
) : HelperResource {

    override fun uploadPlatformLogo(applicationId: String, contentType: String, image: ByteArray): Promise<Unit> {
        return GlobalScope.promise { helperClient.uploadPlatformLogoRequest(applicationId, contentType, image) }
    }
}