package com.doordeck.multiplatform.sdk

import com.doordeck.multiplatform.sdk.TestConstants.DEFAULT_APPLICATION_ID
import com.doordeck.multiplatform.sdk.TestConstants.DEFAULT_DEVICE_ID
import com.doordeck.multiplatform.sdk.TestConstants.DEFAULT_LOCK_ID
import com.doordeck.multiplatform.sdk.TestConstants.DEFAULT_SITE_ID
import com.doordeck.multiplatform.sdk.TestConstants.DEFAULT_TILE_ID
import com.doordeck.multiplatform.sdk.TestConstants.DEFAULT_UPLOAD_URL_PATH
import com.doordeck.multiplatform.sdk.TestConstants.DEFAULT_USER_EMAIL
import com.doordeck.multiplatform.sdk.TestConstants.DEFAULT_USER_ID
import com.doordeck.multiplatform.sdk.api.responses.ApplicationOwnerDetailsResponse
import com.doordeck.multiplatform.sdk.api.responses.ApplicationResponse
import com.doordeck.multiplatform.sdk.api.responses.AuditResponse
import com.doordeck.multiplatform.sdk.api.responses.BatchUserPublicKeyResponse
import com.doordeck.multiplatform.sdk.api.responses.DoorStateResponse
import com.doordeck.multiplatform.sdk.api.responses.FusionLoginResponse
import com.doordeck.multiplatform.sdk.api.responses.GetLogoUploadUrlResponse
import com.doordeck.multiplatform.sdk.api.responses.IntegrationConfigurationResponse
import com.doordeck.multiplatform.sdk.api.responses.IntegrationTypeResponse
import com.doordeck.multiplatform.sdk.api.responses.LockResponse
import com.doordeck.multiplatform.sdk.api.responses.LockUserResponse
import com.doordeck.multiplatform.sdk.api.responses.RegisterEphemeralKeyResponse
import com.doordeck.multiplatform.sdk.api.responses.RegisterEphemeralKeyWithSecondaryAuthenticationResponse
import com.doordeck.multiplatform.sdk.api.responses.ShareableLockResponse
import com.doordeck.multiplatform.sdk.api.responses.SiteLocksResponse
import com.doordeck.multiplatform.sdk.api.responses.SiteResponse
import com.doordeck.multiplatform.sdk.api.responses.TileLocksResponse
import com.doordeck.multiplatform.sdk.api.responses.TokenResponse
import com.doordeck.multiplatform.sdk.api.responses.UserDetailsResponse
import com.doordeck.multiplatform.sdk.api.responses.UserForSiteResponse
import com.doordeck.multiplatform.sdk.api.responses.UserLockResponse
import com.doordeck.multiplatform.sdk.api.responses.UserPublicKeyResponse
import com.doordeck.multiplatform.sdk.internal.api.ApiVersion
import com.doordeck.multiplatform.sdk.internal.api.FusionPaths
import com.doordeck.multiplatform.sdk.internal.api.Paths
import com.doordeck.multiplatform.sdk.util.installContentNegotiation
import com.doordeck.multiplatform.sdk.util.toJson
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.MockRequestHandleScope
import io.ktor.client.engine.mock.respond
import io.ktor.client.engine.mock.respondOk
import io.ktor.client.request.HttpRequestData
import io.ktor.client.request.HttpResponseData
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.utils.io.ByteReadChannel
import io.ktor.utils.io.charsets.Charsets
import io.ktor.utils.io.core.toByteArray

private val TEST_ENGINE = MockEngine { request ->
    val path = request.url.encodedPath
    when (request.method) {
        HttpMethod.Get -> {
            when(path) {
                Paths.getLocksBelongingToTilePath(DEFAULT_TILE_ID) -> respondContent(TILE_LOCKS_RESPONSE)
                Paths.getListSites() -> respondContent(LIST_SITES_RESPONSE)
                Paths.getLocksForSitePath(DEFAULT_SITE_ID) -> respondContent(LOCKS_FOR_SITE_RESPONSE)
                Paths.getUsersForSitePath(DEFAULT_SITE_ID) -> respondContent(USER_FOR_SITE_RESPONSE)
                Paths.getUserDetailsPath() -> respondContent(USER_DETAILS_RESPONSE)
                FusionPaths.getConfigurationTypePath() -> respondContent(INTEGRATION_TYPE_RESPONSE)
                FusionPaths.getDoorStatusPath(DEFAULT_DEVICE_ID) -> respondContent(DOOR_STATE_RESPONSE)
                Paths.getListApplicationsPath() -> respondContent(APPLICATION_LIST_RESPONSE)
                Paths.getApplicationPath(DEFAULT_APPLICATION_ID) -> respondContent(APPLICATION_RESPONSE)
                Paths.getApplicationOwnersDetailsPath(DEFAULT_APPLICATION_ID) -> respondContent(APPLICATION_OWNER_DETAILS_RESPONSE)
                FusionPaths.startDoorPathPath(DEFAULT_DEVICE_ID),
                FusionPaths.stopDoorPathPath(DEFAULT_DEVICE_ID) -> respondOk()
                Paths.getSingleLockPath(DEFAULT_LOCK_ID) -> respondContent(LOCK_RESPONSE)
                Paths.getLockAuditTrailPath(DEFAULT_LOCK_ID) -> respondContent(AUDIT_RESPONSE)
                Paths.getAuditForUserPath(DEFAULT_USER_ID) -> respondContent(AUDIT_RESPONSE)
                Paths.getUsersForLockPath(DEFAULT_LOCK_ID) -> respondContent(USER_LOCK_RESPONSE)
                Paths.getLocksForUserPath(DEFAULT_USER_ID) -> respondContent(LOCK_USER_RESPONSE)
                Paths.getPinnedLocksPath() -> respondContent(PINNED_LOCKS_RESPONSE)
                Paths.getShareableLocksPath() -> respondContent(SHAREABLE_LOCKS_RESPONSE)
                else -> error("Unlinked ${request.method} request $request")
            }
        }
        HttpMethod.Post -> {
            when(path) {
                Paths.getLoginPath(),
                Paths.getRegistrationPath(),
                Paths.getRefreshTokenPath() -> respondContent(TOKEN_RESPONSE)
                Paths.getLogoutPath(),
                Paths.getUpdateUserDetailsPath(),
                Paths.getChangePasswordPath(),
                Paths.getReverifyEmailPath(),
                Paths.getUpdateApplicationPath(DEFAULT_APPLICATION_ID),
                FusionPaths.getEnableDoorPath(),
                Paths.getCreateApplicationPath(),
                Paths.getUpdateApplicationPath(DEFAULT_APPLICATION_ID),
                Paths.getAddAuthKeyPath(DEFAULT_APPLICATION_ID),
                Paths.getAddAuthIssuerPath(DEFAULT_APPLICATION_ID),
                Paths.getAddCorsDomainPath(DEFAULT_APPLICATION_ID),
                Paths.getAddApplicationOwnerPath(DEFAULT_APPLICATION_ID),
                Paths.getOperationPath(DEFAULT_LOCK_ID),
                Paths.getPasswordResetPath(),
                Paths.getPasswordResetVerifyPath() -> respondOk()
                Paths.getRegisterEphemeralKeyPath(),
                Paths.getVerifyEphemeralKeyRegistrationPath() -> respondContent(REGISTER_EPHEMERAL_KEY_RESPONSE)
                Paths.getRegisterEphemeralKeyWithSecondaryAuthenticationPath() -> respondContent(REGISTER_EPHEMERAL_KEY_WITH_SECONDARY_AUTHENTICATION_RESPONSE)
                Paths.getLogoUploadUrlPath(DEFAULT_APPLICATION_ID) -> respondContent(LOGO_UPLOAD_URL_RESPONSE)
                FusionPaths.getLoginPath() -> respondContent(FUSION_LOGIN_RESPONSE)
                FusionPaths.getIntegrationConfiguration() -> respondContent(INTEGRATION_CONFIGURATION_RESPONSE)
                Paths.getUserPublicKeyPath() -> if (request.isVersion(ApiVersion.VERSION_2)) {
                    respondContent(BATCH_USER_PUBLIC_KEY_RESPONSE)
                } else {
                    respondContent(USER_PUBLIC_KEY_RESPONSE)
                }
                Paths.getUserPublicKeyPath(DEFAULT_USER_EMAIL) -> respondContent(USER_PUBLIC_KEY_RESPONSE)
                else -> error("Unlinked ${request.method} request $request")
            }
        }
        HttpMethod.Put -> {
            when(path) {
                Paths.getVerifyEmailPath(),
                Paths.getAssociateMultipleLocksToASingleTilePath(DEFAULT_TILE_ID),
                DEFAULT_UPLOAD_URL_PATH,
                Paths.getUpdateLockPropertiesPath(DEFAULT_LOCK_ID) -> respondOk()
                else -> error("Unlinked ${request.method} request $request")
            }
        }
        HttpMethod.Delete -> {
            when(path) {
                Paths.getDeleteAccountPath(),
                FusionPaths.getDeleteDoorPath(DEFAULT_DEVICE_ID),
                Paths.getDeleteApplicationPath(DEFAULT_APPLICATION_ID),
                Paths.getDeleteAuthIssuerPath(DEFAULT_APPLICATION_ID),
                Paths.getRemoveCorsDomainPath(DEFAULT_APPLICATION_ID),
                Paths.getRemoveApplicationOwnerPath(DEFAULT_APPLICATION_ID) -> respondOk()
                else -> error("Unlinked ${request.method} request $request")
            }
        }
        else -> error("Unlinked method ${request.method}")
    }
}

internal val TEST_MOCK_HTTP_CLIENT = HttpClient(TEST_ENGINE)  {
    installContentNegotiation()
}
internal val TEST_FUSION_CLIENT = createFusionHttpClient()
internal val TEST_CLOUD_CLIENT = createCloudHttpClient()
internal val TEST_HTTP_CLIENT = createHttpClient()

private inline fun <reified T> MockRequestHandleScope.respondContent(content: T): HttpResponseData =
    respond(
        content = ByteReadChannel(content.toJson().toByteArray(Charsets.UTF_8)),
        status = HttpStatusCode.OK,
        headers = headersOf(HttpHeaders.ContentType, "application/json")
    )

private fun HttpRequestData.isVersion(apiVersion: ApiVersion): Boolean =
    headers.contains(HttpHeaders.Accept, "application/vnd.doordeck.api-v${apiVersion.version}+json")

internal val TOKEN_RESPONSE: TokenResponse = randomTokenResponse()
internal val TILE_LOCKS_RESPONSE: TileLocksResponse = randomTileLocksResponse()
internal val LIST_SITES_RESPONSE: List<SiteResponse> = (1..3).map { randomSiteResponse() }
internal val LOCKS_FOR_SITE_RESPONSE: List<SiteLocksResponse> = (1..3).map { randomSiteLocksResponse() }
internal val USER_FOR_SITE_RESPONSE: List<UserForSiteResponse> = (1..3).map { randomUserForSiteResponse() }
internal val REGISTER_EPHEMERAL_KEY_RESPONSE: RegisterEphemeralKeyResponse = randomRegisterEphemeralKeyResponse()
internal val REGISTER_EPHEMERAL_KEY_WITH_SECONDARY_AUTHENTICATION_RESPONSE: RegisterEphemeralKeyWithSecondaryAuthenticationResponse = randomRegisterEphemeralKeyWithSecondaryAuthenticationResponse()
internal val USER_DETAILS_RESPONSE: UserDetailsResponse = randomUserDetailsResponse()
internal val LOGO_UPLOAD_URL_RESPONSE: GetLogoUploadUrlResponse = randomGetLogoUploadUrlResponse()
internal val FUSION_LOGIN_RESPONSE: FusionLoginResponse = randomFusionLoginResponse()
internal val INTEGRATION_TYPE_RESPONSE: IntegrationTypeResponse = randomIntegrationTypeResponse()
internal val INTEGRATION_CONFIGURATION_RESPONSE: List<IntegrationConfigurationResponse> = (1..3).map { randomIntegrationConfigurationResponse() }
internal val DOOR_STATE_RESPONSE: DoorStateResponse = randomDoorStateResponse()
internal val APPLICATION_RESPONSE: ApplicationResponse = randomApplicationResponse()
internal val APPLICATION_LIST_RESPONSE = (1..3).map { randomApplicationResponse() }
internal val APPLICATION_OWNER_DETAILS_RESPONSE: List<ApplicationOwnerDetailsResponse> = (1..3).map { randomApplicationOwnerDetailsResponse() }
internal val LOCK_RESPONSE: LockResponse = randomLockResponse()
internal val AUDIT_RESPONSE: List<AuditResponse> = (1..3).map { randomAuditResponse() }
internal val USER_LOCK_RESPONSE: List<UserLockResponse> = (1..3).map { randomUserLockResponse() }
internal val LOCK_USER_RESPONSE: LockUserResponse = randomLockUserResponse()
internal val USER_PUBLIC_KEY_RESPONSE: UserPublicKeyResponse = randomUserPublicKeyResponse()
internal val BATCH_USER_PUBLIC_KEY_RESPONSE: List<BatchUserPublicKeyResponse> = (1..3).map { randomBatchUserPublicKeyResponse() }
internal val PINNED_LOCKS_RESPONSE: List<LockResponse> = (1..3).map { randomLockResponse() }
internal val SHAREABLE_LOCKS_RESPONSE: List<ShareableLockResponse> = (1..3).map { randomShareableLockResponse() }