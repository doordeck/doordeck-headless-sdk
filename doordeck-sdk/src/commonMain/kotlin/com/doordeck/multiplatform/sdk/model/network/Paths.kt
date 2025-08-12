package com.doordeck.multiplatform.sdk.model.network

import kotlin.jvm.JvmSynthetic

/**
 * Contains all API endpoint paths for the Doordeck cloud service.
 */
internal object Paths {
    /**
     * Account
     */
    @JvmSynthetic fun getLoginPath() = "/auth/token"
    @JvmSynthetic fun getRegistrationPath() = "/auth/register"
    @JvmSynthetic fun getRefreshTokenPath() = "/auth/token/refresh"
    @JvmSynthetic fun getLogoutPath() = "/auth/token/destroy"
    @JvmSynthetic fun getRegisterEphemeralKeyPath() = "/auth/certificate"
    @JvmSynthetic fun getRegisterEphemeralKeyWithSecondaryAuthenticationPath() = "/auth/certificate/verify"
    @JvmSynthetic fun getVerifyEphemeralKeyRegistrationPath() = "/auth/certificate/check"
    @JvmSynthetic fun getVerifyEmailPath() = "/account/email/verify"
    @JvmSynthetic fun getReverifyEmailPath() = "/account/email/reverify"
    @JvmSynthetic fun getChangePasswordPath() = "/account/password"
    @JvmSynthetic fun getUserDetailsPath() = "/account"
    @JvmSynthetic fun getUpdateUserDetailsPath() = "/account"
    @JvmSynthetic fun getDeleteAccountPath() = "/account"
    @JvmSynthetic fun getPasswordResetPath() = "/account/password/reset/initialize"
    @JvmSynthetic fun getPasswordResetVerifyPath() = "/account/password/reset/verify"

    /**
     * Sites
     */
    @JvmSynthetic fun getListSites() = "/site"
    @JvmSynthetic fun getLocksForSitePath(siteId: String) = "/site/$siteId/device"
    @JvmSynthetic fun getUsersForSitePath(siteId: String) = "/site/$siteId/user"

    /**
     * Tiles
     */
    @JvmSynthetic fun getLocksBelongingToTilePath(tileId: String) = "/tile/$tileId"
    @JvmSynthetic fun getAssociateMultipleLocksToASingleTilePath(tileId: String) = "/tile/$tileId"

    /**
     * Lock operations
     */
    @JvmSynthetic fun getSingleLockPath(lockId: String) = "/device/$lockId"
    @JvmSynthetic fun getLockAuditTrailPath(lockId: String) = "/device/$lockId/log"
    @JvmSynthetic fun getAuditForUserPath(userId: String) = "/user/$userId/log"
    @JvmSynthetic fun getUsersForLockPath(lockId: String) = "/device/$lockId/users"
    @JvmSynthetic fun getLocksForUserPath(userId: String) = "/user/$userId"
    @JvmSynthetic fun getUpdateLockPropertiesPath(lockId: String) = "/device/$lockId"
    @JvmSynthetic fun getUserPublicKeyPath(email: String) = "/share/invite/$email"
    @JvmSynthetic fun getUserPublicKeyPath() = "/directory/query"
    @JvmSynthetic fun getOperationPath(lockId: String) = "/device/$lockId/execute"
    @JvmSynthetic fun getPinnedLocksPath() = "/device/favourite"
    @JvmSynthetic fun getShareableLocksPath() = "/device/shareable"

    /**
     * Platform
     */
    @JvmSynthetic fun getCreateApplicationPath() = "/platform/application"
    @JvmSynthetic fun getListApplicationsPath() = "/platform/application"
    @JvmSynthetic fun getApplicationPath(applicationId: String) = "/platform/application/$applicationId"
    @JvmSynthetic fun getUpdateApplicationPath(applicationId: String) = "/platform/application/$applicationId"
    @JvmSynthetic fun getDeleteApplicationPath(applicationId: String) = "/platform/application/$applicationId"
    @JvmSynthetic fun getLogoUploadUrlPath(applicationId: String) = "/platform/application/$applicationId/logo"
    @JvmSynthetic fun getAddAuthKeyPath(applicationId: String) = "/platform/application/$applicationId/auth/key"
    @JvmSynthetic fun getAddAuthIssuerPath(applicationId: String) = "/platform/application/$applicationId/auth/issuer"
    @JvmSynthetic fun getDeleteAuthIssuerPath(applicationId: String) = "/platform/application/$applicationId/auth/issuer"
    @JvmSynthetic fun getAddCorsDomainPath(applicationId: String) = "/platform/application/$applicationId/cors"
    @JvmSynthetic fun getRemoveCorsDomainPath(applicationId: String) = "/platform/application/$applicationId/cors"
    @JvmSynthetic fun getAddApplicationOwnerPath(applicationId: String) = "/platform/application/$applicationId/owner"
    @JvmSynthetic fun getRemoveApplicationOwnerPath(applicationId: String) = "/platform/application/$applicationId/owner"
    @JvmSynthetic fun getApplicationOwnersDetailsPath(applicationId: String) = "/platform/application/$applicationId/owner"

    /**
     * Determines if a given API path requires authentication.
     */
    @JvmSynthetic
    fun requiresAuth(path: String) = path != getLoginPath() && path != getRegistrationPath() &&
            path != getVerifyEmailPath()
}

/**
 * Contains all API endpoint paths for the Fusion service.
 */
internal object FusionPaths {
    /**
     * Fusion
     */
    @JvmSynthetic fun getLoginPath() = "/api/auth/token"
    @JvmSynthetic fun getConfigurationTypePath() = "/api/configuration/type"
    @JvmSynthetic fun getIntegrationConfiguration() = "/api/configuration"
    @JvmSynthetic fun getEnableDoorPath() = "/api/configuration/enable"
    @JvmSynthetic fun getDeleteDoorPath(deviceId: String) = "/api/configuration/$deviceId"
    @JvmSynthetic fun getDoorStatusPath(deviceId: String) = "/api/controller/state/$deviceId"
    @JvmSynthetic fun startDoorPathPath(deviceId: String) = "/api/controller/state/$deviceId/start"
    @JvmSynthetic fun stopDoorPathPath(deviceId: String) = "/api/controller/state/$deviceId/stop"

    /**
     * Determines if a given API path requires authentication.
     */
    @JvmSynthetic
    fun requiresAuth(path: String) = path != getLoginPath()
}