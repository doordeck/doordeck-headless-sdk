package com.doordeck.multiplatform.sdk.model.network

import kotlin.jvm.JvmSynthetic

/**
 * Contains all API endpoint paths for the Doordeck cloud service.
 */
internal object Paths {
    /**
     * Account
     */
    @JvmSynthetic internal fun getLoginPath() = "/auth/token"
    @JvmSynthetic internal fun getRegistrationPath() = "/auth/register"
    @JvmSynthetic internal fun getRefreshTokenPath() = "/auth/token/refresh"
    @JvmSynthetic internal fun getLogoutPath() = "/auth/token/destroy"
    @JvmSynthetic internal fun getRegisterEphemeralKeyPath() = "/auth/certificate"
    @JvmSynthetic internal fun getRegisterEphemeralKeyWithSecondaryAuthenticationPath() = "/auth/certificate/verify"
    @JvmSynthetic internal fun getVerifyEphemeralKeyRegistrationPath() = "/auth/certificate/check"
    @JvmSynthetic internal fun getVerifyEmailPath() = "/account/email/verify"
    @JvmSynthetic internal fun getReverifyEmailPath() = "/account/email/reverify"
    @JvmSynthetic internal fun getChangePasswordPath() = "/account/password"
    @JvmSynthetic internal fun getUserDetailsPath() = "/account"
    @JvmSynthetic internal fun getUpdateUserDetailsPath() = "/account"
    @JvmSynthetic internal fun getDeleteAccountPath() = "/account"
    @JvmSynthetic internal fun getPasswordResetPath() = "/account/password/reset/initialize"
    @JvmSynthetic internal fun getPasswordResetVerifyPath() = "/account/password/reset/verify"

    /**
     * Sites
     */
    @JvmSynthetic internal fun getListSites() = "/site"
    @JvmSynthetic internal fun getLocksForSitePath(siteId: String) = "/site/$siteId/device"
    @JvmSynthetic internal fun getUsersForSitePath(siteId: String) = "/site/$siteId/user"

    /**
     * Tiles
     */
    @JvmSynthetic internal fun getLocksBelongingToTilePath(tileId: String) = "/tile/$tileId"
    @JvmSynthetic internal fun getAssociateMultipleLocksToASingleTilePath(tileId: String) = "/tile/$tileId"

    /**
     * Lock operations
     */
    @JvmSynthetic internal fun getSingleLockPath(lockId: String) = "/device/$lockId"
    @JvmSynthetic internal fun getLockAuditTrailPath(lockId: String) = "/device/$lockId/log"
    @JvmSynthetic internal fun getAuditForUserPath(userId: String) = "/user/$userId/log"
    @JvmSynthetic internal fun getUsersForLockPath(lockId: String) = "/device/$lockId/users"
    @JvmSynthetic internal fun getLocksForUserPath(userId: String) = "/user/$userId"
    @JvmSynthetic internal fun getUpdateLockPropertiesPath(lockId: String) = "/device/$lockId"
    @JvmSynthetic internal fun getUserPublicKeyPath(email: String) = "/share/invite/$email"
    @JvmSynthetic internal fun getUserPublicKeyPath() = "/directory/query"
    @JvmSynthetic internal fun getOperationPath(lockId: String) = "/device/$lockId/execute"
    @JvmSynthetic internal fun getPinnedLocksPath() = "/device/favourite"
    @JvmSynthetic internal fun getShareableLocksPath() = "/device/shareable"

    /**
     * Platform
     */
    @JvmSynthetic internal fun getCreateApplicationPath() = "/platform/application"
    @JvmSynthetic internal fun getListApplicationsPath() = "/platform/application"
    @JvmSynthetic internal fun getApplicationPath(applicationId: String) = "/platform/application/$applicationId"
    @JvmSynthetic internal fun getUpdateApplicationPath(applicationId: String) = "/platform/application/$applicationId"
    @JvmSynthetic internal fun getDeleteApplicationPath(applicationId: String) = "/platform/application/$applicationId"
    @JvmSynthetic internal fun getLogoUploadUrlPath(applicationId: String) = "/platform/application/$applicationId/logo"
    @JvmSynthetic internal fun getAddAuthKeyPath(applicationId: String) = "/platform/application/$applicationId/auth/key"
    @JvmSynthetic internal fun getAddAuthIssuerPath(applicationId: String) = "/platform/application/$applicationId/auth/issuer"
    @JvmSynthetic internal fun getDeleteAuthIssuerPath(applicationId: String) = "/platform/application/$applicationId/auth/issuer"
    @JvmSynthetic internal fun getAddCorsDomainPath(applicationId: String) = "/platform/application/$applicationId/cors"
    @JvmSynthetic internal fun getRemoveCorsDomainPath(applicationId: String) = "/platform/application/$applicationId/cors"
    @JvmSynthetic internal fun getAddApplicationOwnerPath(applicationId: String) = "/platform/application/$applicationId/owner"
    @JvmSynthetic internal fun getRemoveApplicationOwnerPath(applicationId: String) = "/platform/application/$applicationId/owner"
    @JvmSynthetic internal fun getApplicationOwnersDetailsPath(applicationId: String) = "/platform/application/$applicationId/owner"

    /**
     * Determines if a given API path requires authentication.
     */
    @JvmSynthetic
    internal fun requiresAuth(path: String) = path != getLoginPath() && path != getRegistrationPath() &&
            path != getVerifyEmailPath()
}

/**
 * Contains all API endpoint paths for the Fusion service.
 */
internal object FusionPaths {
    /**
     * Fusion
     */
    @JvmSynthetic internal fun getLoginPath() = "/api/auth/token"
    @JvmSynthetic internal fun getConfigurationTypePath() = "/api/configuration/type"
    @JvmSynthetic internal fun getIntegrationConfiguration() = "/api/configuration"
    @JvmSynthetic internal fun getEnableDoorPath() = "/api/configuration/enable"
    @JvmSynthetic internal fun getDeleteDoorPath(deviceId: String) = "/api/configuration/$deviceId"
    @JvmSynthetic internal fun getDoorStatusPath(deviceId: String) = "/api/controller/state/$deviceId"
    @JvmSynthetic internal fun startDoorPathPath(deviceId: String) = "/api/controller/state/$deviceId/start"
    @JvmSynthetic internal fun stopDoorPathPath(deviceId: String) = "/api/controller/state/$deviceId/stop"

    /**
     * Determines if a given API path requires authentication.
     */
    @JvmSynthetic
    internal fun requiresAuth(path: String) = path != getLoginPath()
}