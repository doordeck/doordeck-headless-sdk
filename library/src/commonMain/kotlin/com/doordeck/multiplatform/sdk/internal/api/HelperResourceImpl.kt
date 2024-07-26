package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.Constants.CDN_URL
import com.doordeck.multiplatform.sdk.api.HelperResource
import com.doordeck.multiplatform.sdk.api.PlatformResource
import com.doordeck.multiplatform.sdk.util.addRequestHeaders
import io.ktor.client.*
import io.ktor.client.request.*

class HelperResourceImpl(
    private val httpClient: HttpClient,
    private val platform: PlatformResource
) : AbstractResourceImpl(), HelperResource {

    override fun uploadPlatformLogo(applicationId: String, contentType: String, image: ByteArray) {
        // Generate a new presigned URL
        val url = platform.getLogoUploadUrl(applicationId, contentType)

        // Upload the image into the presigned URL
        httpClient.put<Unit>(url.uploadUrl) {
            addRequestHeaders(contentType = contentType)
            setBody(image)
        }

        // Build the right url
        val cdnUrl = CDN_URL + url.uploadUrl.split("?")[0].split(".com")[1]

        // Updates the application
        platform.updateApplicationLogoUrl(applicationId, cdnUrl)
    }
}