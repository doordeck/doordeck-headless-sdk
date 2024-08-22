package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.Constants
import com.doordeck.multiplatform.sdk.api.requests.GetLogoUploadUrlRequest
import com.doordeck.multiplatform.sdk.api.requests.UpdateApplicationLogoUrlRequest
import com.doordeck.multiplatform.sdk.api.responses.GetLogoUploadUrlResponse
import com.doordeck.multiplatform.sdk.util.addRequestHeaders
import io.ktor.client.HttpClient
import io.ktor.client.request.setBody

abstract class AbstractHelperClientImpl(
    private val httpClient: HttpClient,
    private val cloudHttpClient: HttpClient
) : AbstractResourceImpl() {

    suspend fun uploadPlatformLogoRequest(applicationId: String, contentType: String, image: ByteArray) {
        // Generate a new presigned URL
        val url = cloudHttpClient.post<GetLogoUploadUrlResponse>(Paths.getLogoUploadUrlPath(applicationId)) {
            addRequestHeaders()
            setBody(GetLogoUploadUrlRequest(contentType))
        }
        // Upload the image into the presigned URL
        httpClient.put<Unit>(url.uploadUrl) {
            addRequestHeaders(contentType = contentType)
            setBody(image)
        }
        // Build the right url
        val cdnUrl = Constants.CDN_URL + url.uploadUrl.split("?")[0].split(".com")[1]
        // Updates the application
        httpClient.post<Unit>(Paths.getUpdateApplicationPath(applicationId)) {
            addRequestHeaders()
            setBody(UpdateApplicationLogoUrlRequest(cdnUrl))
        }
    }
}