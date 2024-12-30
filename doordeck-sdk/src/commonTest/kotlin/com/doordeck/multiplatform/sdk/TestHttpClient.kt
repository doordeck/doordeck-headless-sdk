package com.doordeck.multiplatform.sdk

import com.doordeck.multiplatform.sdk.TestConstants.DEFAULT_APPLICATION_ID
import com.doordeck.multiplatform.sdk.TestConstants.DEFAULT_DEVICE_ID
import com.doordeck.multiplatform.sdk.TestConstants.DEFAULT_LOCK_ID
import com.doordeck.multiplatform.sdk.TestConstants.DEFAULT_SITE_ID
import com.doordeck.multiplatform.sdk.TestConstants.DEFAULT_TILE_ID
import com.doordeck.multiplatform.sdk.TestConstants.DEFAULT_UPLOAD_URL
import com.doordeck.multiplatform.sdk.TestConstants.DEFAULT_UPLOAD_URL_PATH
import com.doordeck.multiplatform.sdk.TestConstants.DEFAULT_USER_EMAIL
import com.doordeck.multiplatform.sdk.TestConstants.DEFAULT_USER_ID
import com.doordeck.multiplatform.sdk.api.model.AuditEvent
import com.doordeck.multiplatform.sdk.api.model.TwoFactorMethod
import com.doordeck.multiplatform.sdk.api.model.UserRole
import com.doordeck.multiplatform.sdk.api.responses.ApplicationOwnerDetailsResponse
import com.doordeck.multiplatform.sdk.api.responses.ApplicationResponse
import com.doordeck.multiplatform.sdk.api.responses.AuditIssuerResponse
import com.doordeck.multiplatform.sdk.api.responses.AuditResponse
import com.doordeck.multiplatform.sdk.api.responses.BatchUserPublicKeyResponse
import com.doordeck.multiplatform.sdk.api.responses.DoorStateResponse
import com.doordeck.multiplatform.sdk.api.responses.EcKeyResponse
import com.doordeck.multiplatform.sdk.api.responses.EmailPreferencesResponse
import com.doordeck.multiplatform.sdk.api.responses.FusionLoginResponse
import com.doordeck.multiplatform.sdk.api.responses.GetLogoUploadUrlResponse
import com.doordeck.multiplatform.sdk.api.responses.IntegrationConfigurationResponse
import com.doordeck.multiplatform.sdk.api.responses.IntegrationTypeResponse
import com.doordeck.multiplatform.sdk.api.responses.LockResponse
import com.doordeck.multiplatform.sdk.api.responses.LockSettingsResponse
import com.doordeck.multiplatform.sdk.api.responses.LockStateResponse
import com.doordeck.multiplatform.sdk.api.responses.LockUserResponse
import com.doordeck.multiplatform.sdk.api.responses.RegisterEphemeralKeyResponse
import com.doordeck.multiplatform.sdk.api.responses.RegisterEphemeralKeyWithSecondaryAuthenticationResponse
import com.doordeck.multiplatform.sdk.api.responses.ServiceStateType
import com.doordeck.multiplatform.sdk.api.responses.ShareableLockResponse
import com.doordeck.multiplatform.sdk.api.responses.SiteLockSettingsResponse
import com.doordeck.multiplatform.sdk.api.responses.SiteLocksResponse
import com.doordeck.multiplatform.sdk.api.responses.SiteResponse
import com.doordeck.multiplatform.sdk.api.responses.TileLocksResponse
import com.doordeck.multiplatform.sdk.api.responses.TokenResponse
import com.doordeck.multiplatform.sdk.api.responses.UserDetailsResponse
import com.doordeck.multiplatform.sdk.api.responses.UserForSiteResponse
import com.doordeck.multiplatform.sdk.api.responses.UserLockResponse
import com.doordeck.multiplatform.sdk.api.responses.UserPublicKeyResponse
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
                Paths.getUserPublicKeyPath() -> if (request.isVersionTwo()) {
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

val TEST_MOCK_HTTP_CLIENT = HttpClient(TEST_ENGINE)  {
    installContentNegotiation()
}
val TEST_FUSION_CLIENT = createFusionHttpClient()
val TEST_CLOUD_CLIENT = createCloudHttpClient()
val TEST_HTTP_CLIENT = createHttpClient()

private fun MockRequestHandleScope.respondContent(content: String): HttpResponseData =
    respond(
        content = ByteReadChannel(content.toByteArray(Charsets.UTF_8)),
        status = HttpStatusCode.OK,
        headers = headersOf(HttpHeaders.ContentType, "application/json")
    )

private fun HttpRequestData.isVersionTwo(): Boolean =
    headers.contains(HttpHeaders.Accept, "application/vnd.doordeck.api-v2+json")

private val TOKEN_RESPONSE = TokenResponse("", "").toJson()
private val TILE_LOCKS_RESPONSE = TileLocksResponse("", "", emptyList()).toJson()
private val LIST_SITES_RESPONSE = listOf(SiteResponse("", "", "", 0.0, 0.0, 0, "", "", "")).toJson()
private val LOCKS_FOR_SITE_RESPONSE = listOf(SiteLocksResponse("", "", null, UserRole.USER, SiteLockSettingsResponse(0.0, emptyList(), "", emptyList()))).toJson()
private val USER_FOR_SITE_RESPONSE = listOf(UserForSiteResponse("", "", null, false)).toJson()
private val REGISTER_EPHEMERAL_KEY_RESPONSE = RegisterEphemeralKeyResponse(listOf(""), "").toJson()
private val REGISTER_EPHEMERAL_KEY_WITH_SECONDARY_AUTHENTICATION_RESPONSE = RegisterEphemeralKeyWithSecondaryAuthenticationResponse(TwoFactorMethod.EMAIL).toJson()
private val USER_DETAILS_RESPONSE = UserDetailsResponse("", null, true, "").toJson()
private val LOGO_UPLOAD_URL_RESPONSE = GetLogoUploadUrlResponse(DEFAULT_UPLOAD_URL).toJson()
private val FUSION_LOGIN_RESPONSE = FusionLoginResponse("").toJson()
private val INTEGRATION_TYPE_RESPONSE = IntegrationTypeResponse().toJson()
private val INTEGRATION_CONFIGURATION_RESPONSE = listOf(IntegrationConfigurationResponse()).toJson()
private val DOOR_STATE_RESPONSE = DoorStateResponse(ServiceStateType.RUNNING).toJson()
private val APPLICATION_RESPONSE = ApplicationResponse("", "", emailPreferences = EmailPreferencesResponse(primaryColour = "", secondaryColour = ""), authKeys = mapOf("" to EcKeyResponse("EC", "use", "kid", crv = "crv", x = "x", y = "y"))).toJson()
private val APPLICATION_LIST_RESPONSE = listOf(ApplicationResponse("", "", emailPreferences = EmailPreferencesResponse(primaryColour = "", secondaryColour = ""), authKeys = mapOf("" to EcKeyResponse("EC", "use", "kid", crv = "crv", x = "x", y = "y")))).toJson()
private val APPLICATION_OWNER_DETAILS_RESPONSE = listOf(ApplicationOwnerDetailsResponse("", "", null, false, false)).toJson()
private val LOCK_RESPONSE = LockResponse("", "", null, null, null, UserRole.USER, LockSettingsResponse(0.0, emptyList(), "", tiles = emptyList(), hidden = false), LockStateResponse(false, false), false).toJson()
private val AUDIT_RESPONSE = listOf(AuditResponse("", 0.0, AuditEvent.DOOR_LOCK, AuditIssuerResponse(""), null, null, false)).toJson()
private val USER_LOCK_RESPONSE = listOf(UserLockResponse("", "", "", null, false, false, UserRole.USER)).toJson()
private val LOCK_USER_RESPONSE = LockUserResponse("", "", "", null, false, false, null, null, emptyList()).toJson()
private val USER_PUBLIC_KEY_RESPONSE = UserPublicKeyResponse("", "").toJson()
private val BATCH_USER_PUBLIC_KEY_RESPONSE = listOf(BatchUserPublicKeyResponse("", publicKey = "")).toJson()
private val PINNED_LOCKS_RESPONSE = listOf(LockResponse("", "", null, null, null, UserRole.USER, LockSettingsResponse(0.0, emptyList(), "", tiles = emptyList(), hidden = false), LockStateResponse(false, false), false)).toJson()
private val SHAREABLE_LOCKS_RESPONSE = listOf(ShareableLockResponse("", "")).toJson()