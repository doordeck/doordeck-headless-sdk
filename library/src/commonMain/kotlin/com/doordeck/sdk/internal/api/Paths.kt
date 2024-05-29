package com.doordeck.sdk.internal.api

object Paths {
    // Account
    fun getLoginPath() = "/auth/token"
    fun getRegistrationPath() = "/auth/register"
    fun getRefreshTokenPath() = "/auth/token/refresh"
    fun getLogoutPath() = "/auth/token/destroy"
    fun getRegisterEphemeralKeyPath() = "/auth/certificate"
    fun getRegisterEphemeralKeyWithSecondaryAuthenticationPath() = "/auth/certificate/verify"
    fun getVerifyEphemeralKeyRegistrationPath() = "/auth/certificate/check"
    fun getVerifyEmailPath() = "/account/email/verify"
    fun getReverifyEmailPath() = "/account/email/reverify"
    fun getChangePasswordPath() = "/account/password"
    fun getUserDetailsPath() = "/account"
    fun getUpdateUserDetailsPath() = "/account"
    fun getDeleteAccountPath() = "/account"

    // Sites
    fun getListSites() = "/site"
    fun getLocksForSitePath(siteId: String) = "/site/$siteId/device"
    fun getUsersForASitePath(siteId: String) = "/site/$siteId/user"

    // Tiles
    fun getLocksBelongingToTilePath(tileId: String) = "/tile/$tileId"
    fun getAssociateTileWithLockPath(tileId: String, deviceId: String) = "/device/$deviceId/tile/$tileId"
    fun getDisassociateTileFromLockPath(tileId: String, deviceId: String) = "/device/$deviceId/tile/$tileId"
    fun getAssociateMultipleLocksToASingleTilePath(tileId: String) = "/tile/$tileId"

    // Lock Operations
    fun getAllLocksPath() = "/device"
    fun getASingleLockPath(lockId: String) = "/device/$lockId"
    fun getLockAuditTrailPath(lockId: String) = "/device/$lockId/log" // add start/end as params - ?start=0000&end=0000"
    fun getAuditForAUserPath(lockId: String) = "/user/$lockId/log" // add start/end as params - ?start=0000&end=0000"
    fun getUsersForALockPath(lockId: String) = "/device/$lockId/users"
    fun getLocksForAUserPath(userId: String) = "/user/$userId"
    fun getUpdateLockPropertiesPath(lockId: String) = "/device/$lockId"
    fun getPairWithNewLockPath() = "/device"
    fun getDoordeckUserPublickKeyPath(email: String) = "/share/invite/$email"
    fun getUserPublicKeyPath() = "/directory/query"
    fun getUnlockPath(lockId: String) = "/device/$lockId/execute"
    fun getShareALockPath(lockId: String) = "/device/$lockId/execute"
    fun getRevokeAccessToALockPath(lockId: String) = "/device/$lockId/execute"
    fun getUpdateSecureSettingsPath(lockId: String) = "/device/$lockId/execute"
    fun getPinnedLocksPath() = "/device/favourite"
    fun getShareableLocksPath() = "/device/shareable"

    // Platform
    fun getCreateApplicationPath() = "/platform/application"
    fun getListApplicationsPath() = "/platform/application"
    fun getApplicationPath(applicationId: String) = "/platform/application/$applicationId"
    fun getUpdateApplicationPath(applicationId: String) = "/platform/application/$applicationId"
    fun getDeleteApplicationPath(applicationId: String) = "/platform/application/$applicationId"
    fun getLogoUploadUrlPath(applicationId: String) = "/platform/application/$applicationId/logo"
    fun getAddAuthKeyPath(applicationId: String) = "/platform/application/$applicationId/auth/key"
    fun getAddAuthIssuerPath(applicationId: String) = "/platform/application/$applicationId/auth/issuer"
    fun getDeleteAuthIssuerPath(applicationId: String) = "/platform/application/$applicationId/auth/issuer"
    fun getAddCorsDomainPath(applicationId: String) = "/platform/application/$applicationId/cors"
    fun getRemoveCorsDomainPath(applicationId: String) = "/platform/application/$applicationId/cors"
    fun getAddApplicationOwnerPath(applicationId: String) = "/platform/application/$applicationId/owner"
    fun getRemoveApplicationOwnerPath(applicationId: String) = "/platform/application/$applicationId/owner"
    fun getApplicationOwnersDetailsPath(applicationId: String) = "/platform/application/$applicationId/owner"
}