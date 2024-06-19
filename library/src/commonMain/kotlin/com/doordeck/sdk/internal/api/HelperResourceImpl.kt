package com.doordeck.sdk.internal.api

import com.doordeck.sdk.Constants.CDN_URL
import com.doordeck.sdk.api.HelperResource
import com.doordeck.sdk.util.addRequestHeaders
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*

class HelperResourceImpl(
    private val httpClient: HttpClient,
    private val platform: PlatformResourceImpl
) : AbstractResourceImpl(), HelperResource {

    override fun uploadPlatformLogo(applicationId: String, contentType: String, image: ByteArray) {
        // Generate a new presigned URL
        val url = platform.getLogoUploadUrl(applicationId, contentType)

        // Upload the image into the presigned URL
        httpClient.putEmpty(url.uploadUrl) {
            addRequestHeaders(headers = mapOf(HttpHeaders.ContentType to contentType))
            setBody(image)
        }

        // Build the right url
        val cdnUrl = CDN_URL + url.uploadUrl.split("?")[0].split(".com")[1]

        // Updates the application
        platform.updateApplicationLogoUrl(applicationId, cdnUrl)
    }
}